package advent

import java.io.File

private val parser = """(.*) -> (.*)""".toRegex()

data class TuringBlueprint(
        val start: Char,
        val numSteps: Int,
        val states: Map<Char, String>
)

fun parseTuringBlueprint(input: String): TuringBlueprint {
    val start = input.lines().first()[0]
    val numSteps = input.lines().drop(1).first().toInt()
    val states = HashMap<Char, String>()
    input.lines().drop(2).forEach { line ->
        val m = parser.matchEntire(line) ?: error("could not parse $line")
        val s = m.groupValues[1][0]
        val logic = m.groupValues[2]
        states.put(s, logic)
    }
    return TuringBlueprint(start, numSteps, states)
}

fun runTuringBlueprint(blueprint: TuringBlueprint): Int {
    val tape = IntArray(100000)
    var currentState = blueprint.start
    var currentPos = tape.size / 2
    repeat(blueprint.numSteps) {
        val logic = blueprint.states[currentState]!!
        logic.split(", ")[tape[currentPos]].let {
            tape[currentPos] = it[0].toInt() - '0'.toInt()
            if (it[1] == 'L')
                currentPos--
            else
                currentPos++
            currentState = it[2]
        }
    }
    return tape.sum()
}

fun day25() {
    val input = File("day25.txt").readText()
    val blueprint = parseTuringBlueprint(input)
    println("Day 25 Part 1: ${runTuringBlueprint(blueprint)}")
}