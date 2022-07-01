package demo

import java.util.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sign

internal class Automata(val width: Int, val height: Int) {
    var seed = "Automata"
    var randomFillPercent = 50

    private var useRandomSeed = true
    private val map = Bitmap(width, height)

    operator fun get(x: Int, y: Int) = map[x, y]

    fun generate() {
        randomFillMap()
        for (i in 0..4) {
            smoothMap()
        }

        drawCircle(Coordinate(width shr 1, height shr 1), 10)
        processMap()

        val borderSize = 1
        val borderedMap = Array(width + borderSize * 2) { IntArray(height + borderSize * 2) }
        for (x in borderedMap.indices) {
            for (y in borderedMap[0].indices) {
                if (x >= borderSize && x < width + borderSize && y >= borderSize && y < height + borderSize) {
                    borderedMap[x][y] = map[x - borderSize, y - borderSize]
                } else {
                    borderedMap[x][y] = 1
                }
            }
        }
    }

    private fun processMap() {
        val wallRegions = getRegions(1)
        val wallThresholdSize = 50
        wallRegions.stream().filter { wallRegion -> wallRegion.size < wallThresholdSize }.forEach { wallRegion ->
            for (tile in wallRegion) {
                map[tile.x, tile.y] = 0
            }
        }

        val roomRegions = getRegions(0)
        val roomThresholdSize = 50
        val survivingRooms: MutableList<Room> = ArrayList()
        for (roomRegion in roomRegions) {
            if (roomRegion.size < roomThresholdSize) {
                for (tile in roomRegion) {
                    map[tile.x, tile.y] = 1
                }
            } else {
                survivingRooms.add(Room(roomRegion, map))
            }
        }

        survivingRooms.sort()
        survivingRooms[0].isMainRoom = true
        survivingRooms[0].isAccessibleFromMainRoom = true
        connectClosestRooms(survivingRooms)
    }

    private fun connectClosestRooms(allRooms: MutableList<Room>, forceAccessibilityFromMainRoom: Boolean = false) {
        var roomListA = mutableListOf<Room>()
        var roomListB = mutableListOf<Room>()
        if (forceAccessibilityFromMainRoom) {
            for (room in allRooms) {
                if (room.isAccessibleFromMainRoom) {
                    roomListB.add(room)
                } else {
                    roomListA.add(room)
                }
            }
        } else {
            roomListA = allRooms
            roomListB = allRooms
        }
        var bestDistance = 0
        var bestTileA = Coordinate(0, 0)
        var bestTileB = Coordinate(0, 0)
        var bestRoomA = Room()
        var bestRoomB = Room()
        var possibleConnectionFound = false
        for (roomA in roomListA) {
            if (!forceAccessibilityFromMainRoom) {
                possibleConnectionFound = false
                if (roomA.connectedRooms.size > 0) {
                    continue
                }
            }
            for (roomB in roomListB) {
                if (roomA === roomB || roomA.isConnected(roomB)) {
                    continue
                }
                for (tileIndexA in roomA.edgeTiles.indices) {
                    for (tileIndexB in roomB.edgeTiles.indices) {
                        val tileA = roomA.edgeTiles[tileIndexA]
                        val tileB = roomB.edgeTiles[tileIndexB]
                        val distanceBetweenRooms = ((tileA.x - tileB.x).toDouble()
                            .pow(2.0) + (tileA.y - tileB.y).toDouble().pow(2.0)).toInt()
                        if (distanceBetweenRooms < bestDistance || !possibleConnectionFound) {
                            bestDistance = distanceBetweenRooms
                            possibleConnectionFound = true
                            bestTileA = tileA
                            bestTileB = tileB
                            bestRoomA = roomA
                            bestRoomB = roomB
                        }
                    }
                }
            }
            if (possibleConnectionFound && !forceAccessibilityFromMainRoom) {
                createPassage(bestRoomA, bestRoomB, bestTileA, bestTileB)
            }
        }
        if (possibleConnectionFound && forceAccessibilityFromMainRoom) {
            createPassage(bestRoomA, bestRoomB, bestTileA, bestTileB)
            connectClosestRooms(allRooms, true)
        }
        if (!forceAccessibilityFromMainRoom) {
            connectClosestRooms(allRooms, true)
        }
    }

    private fun createPassage(roomA: Room, roomB: Room, tileA: Coordinate, tileB: Coordinate) {
        Room.connectRooms(roomA, roomB)
        val line = getLine(tileA, tileB)
        for (c in line) {
            drawCircle(c, 5)
        }
    }

    private fun drawCircle(c: Coordinate, r: Int) {
        for (x in -r..r) {
            for (y in -r..r) {
                if (x * x + y * y <= r * r) {
                    val drawX = c.x + x
                    val drawY = c.y + y
                    if (isInMapRange(drawX, drawY)) {
                        map[drawX, drawY] = 0
                    }
                }
            }
        }
    }

    private fun getLine(from: Coordinate, to: Coordinate): List<Coordinate> {
        val line: MutableList<Coordinate> = ArrayList()
        var x = from.x
        var y = from.y
        val dx = to.x - from.x
        val dy = to.y - from.y
        var inverted = false
        var step = sign(dx.toFloat()).toInt()
        var gradientStep = sign(dy.toFloat()).toInt()
        var longest = abs(dx)
        var shortest = abs(dy)
        if (longest < shortest) {
            inverted = true
            longest = abs(dy)
            shortest = abs(dx)
            step = sign(dy.toFloat()).toInt()
            gradientStep = sign(dx.toFloat()).toInt()
        }
        var gradientAccumulation = longest / 2
        for (i in 0 until longest) {
            line.add(Coordinate(x, y))
            if (inverted) {
                y += step
            } else {
                x += step
            }
            gradientAccumulation += shortest
            if (gradientAccumulation >= longest) {
                if (inverted) {
                    x += gradientStep
                } else {
                    y += gradientStep
                }
                gradientAccumulation -= longest
            }
        }
        return line
    }

    private fun getRegions(tileType: Int): List<List<Coordinate>> {
        val regions = mutableListOf<List<Coordinate>>()
        val mapFlags = Array(width) { IntArray(height) }
        for (x in 0 until width) {
            for (y in 0 until height) {
                if (mapFlags[x][y] == 0 && map[x, y] == tileType) {
                    val newRegion = getRegionTiles(x, y)
                    for (tile in newRegion) {
                        mapFlags[tile.x][tile.y] = 1
                    }
                    regions.add(newRegion)
                }
            }
        }
        return regions
    }

    private fun getRegionTiles(startX: Int, startY: Int): List<Coordinate> {
        val tiles = mutableListOf<Coordinate>()
        val mapFlags = Array(width) { IntArray(height) }
        val tileType = map[startX, startY]
        val queue = LinkedList<Coordinate>()
        queue.add(Coordinate(startX, startY))
        mapFlags[startX][startY] = 1
        while (queue.size > 0) {
            val tile = queue.remove()
            tiles.add(tile)
            for (x in tile.x - 1..tile.x + 1) {
                for (y in tile.y - 1..tile.y + 1) {
                    if (isInMapRange(x, y) && (y == tile.y || x == tile.x)) {
                        if (mapFlags[x][y] == 0 && map[x, y] == tileType) {
                            mapFlags[x][y] = 1
                            queue.add(Coordinate(x, y))
                        }
                    }
                }
            }
        }
        return tiles
    }

    private fun isInMapRange(x: Int, y: Int) = x in 0 until width && y >= 0 && y < height

    private fun randomFillMap() {
        if (useRandomSeed) seed = Date().time.toString()
        val pseudoRandom = Random(seed.hashCode().toLong())

        for (x in 0 until width) {
            for (y in 0 until height) {
                if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
                    map[x, y] = 1
                } else {
                    map[x, y] = if (pseudoRandom.nextInt(100) < randomFillPercent) 1 else 0
                }
            }
        }
    }

    private fun smoothMap() {
        for (x in 0 until width) {
            for (y in 0 until height) {
                val neighbourWallTiles = getSurroundingWallCount(x, y)
                if (neighbourWallTiles > 4) {
                    map[x, y] = 1
                } else if (neighbourWallTiles < 4) {
                    map[x, y] = 0
                }
            }
        }
    }

    private fun getSurroundingWallCount(gridX: Int, gridY: Int): Int {
        var wallCount = 0
        for (neighbourX in gridX - 1..gridX + 1) {
            for (neighbourY in gridY - 1..gridY + 1) {
                if (isInMapRange(neighbourX, neighbourY)) {
                    if (neighbourX != gridX || neighbourY != gridY) {
                        wallCount += map[neighbourX, neighbourY]
                    }
                } else {
                    wallCount++
                }
            }
        }
        return wallCount
    }

    private class Room(tiles: List<Coordinate> = emptyList(), map: Bitmap = Bitmap(1, 1)) : Comparable<Room> {
        var isMainRoom = false
        var roomSize = tiles.size
        var isAccessibleFromMainRoom = false
        var connectedRooms = mutableListOf<Room>()
        var edgeTiles = mutableListOf<Coordinate>()

        init {
            for (tile in tiles) {
                for (x in (tile.x - 1)..(tile.x + 1)) {
                    for (y in (tile.y - 1)..(tile.y + 1)) {
                        if (x == tile.x || y == tile.y) {
                            if (map[x, y] == 1) {
                                edgeTiles.add(tile)
                            }
                        }
                    }
                }
            }
        }

        fun setAccessibleFromMainRoom() {
            if (!isAccessibleFromMainRoom) {
                isAccessibleFromMainRoom = true
                connectedRooms.forEach { it.setAccessibleFromMainRoom() }
            }
        }

        fun isConnected(other: Room) = connectedRooms.contains(other)

        override fun compareTo(other: Room) = other.roomSize.compareTo(roomSize)

        companion object {
            fun connectRooms(roomA: Room, roomB: Room) {
                if (roomA.isAccessibleFromMainRoom) {
                    roomB.setAccessibleFromMainRoom()
                } else if (roomB.isAccessibleFromMainRoom) {
                    roomA.setAccessibleFromMainRoom()
                }
                roomA.connectedRooms.add(roomB)
                roomB.connectedRooms.add(roomA)
            }
        }
    }

    private data class Coordinate(val x: Int, val y: Int)
}
