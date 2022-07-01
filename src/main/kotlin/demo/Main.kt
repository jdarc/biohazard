package demo

fun main(args: Array<String>) {
    val demos = "circles,equations,packman,rostering,worms"
    val name = args.firstOrNull { demos.contains(it) }.orEmpty()
    if (name == "") {
        val available = java.lang.String.join("|", *demos.split(",").toTypedArray())
        println("Usage: ./gradlew -q --console plain run --args=\"[$available]\"")
    } else {
        System.setProperty("sun.java2d.uiScale.enabled", "false")
        when (name) {
            "circles" -> demo.circles.Program.main()
            "equations" -> demo.equations.Program.main()
            "packman" -> demo.packman.Program.main()
            "rostering" -> demo.rostering.Program.main()
            "worms" -> demo.worms.Program.main()
        }
    }
}
