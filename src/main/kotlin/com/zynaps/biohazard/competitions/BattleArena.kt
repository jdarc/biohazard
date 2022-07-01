package com.zynaps.biohazard.competitions

import com.zynaps.biohazard.Competition
import com.zynaps.biohazard.Tribe
import com.zynaps.biohazard.generators.Rng
import com.zynaps.biohazard.generators.ThreadLocalRng
import kotlin.math.ceil
import kotlin.math.max

class BattleArena(probability: Double = 0.1, ratio: Double = 0.1, private val random: Rng = ThreadLocalRng()) :
    Competition {

    private val probability = probability.coerceIn(0.0, 1.0)
    private val ratio = ratio.coerceIn(0.0001, 1.0)

    override fun invoke(tribes: List<Tribe>) {
        if (random.next() > probability) return

        val (heroTribe, homeTribe) = tribes.shuffled().take(2).sortedByDescending { it.champion.fitness }
        val hero = heroTribe.champion
        if (hero.fitness <= 0.0) return

        val contestants = ceil(homeTribe.parents.size * ratio).toInt() - 1
        val warriors = homeTribe.parents.sortedByDescending { it.fitness }.take(contestants)

        for (warrior in warriors) {
            val warriorFitness = max(0.0, warrior.fitness)
            val totalFitness = warriorFitness + hero.fitness
            if (warriorFitness / totalFitness < random.next()) {
                val offset = random.nextInt(hero.genes)
                val length = random.nextInt(hero.genes - offset + 1)
                val creature = homeTribe.parents[random.nextInt(homeTribe.parents.size)]
                creature.splice(hero, offset, offset, length)
            }
        }
    }
}
