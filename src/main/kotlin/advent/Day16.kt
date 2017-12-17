package advent

import java.io.File

private val spin = "s(\\d+)".toRegex()
private val exchange = "x(\\d+)/(\\d+)".toRegex()
private val partner = "p(\\w+)/(\\w+)".toRegex()

fun dance(programs: String, moves: List<String>): String {
    var p = programs.toCharArray().toMutableList()
    for (line in moves) {
        spin.matchEntire(line)?.let { mr ->
            val x = mr.groupValues[1].toInt()
            p = (p.drop(p.size - x) + p.take(p.size - x)).toMutableList()
        }
        exchange.matchEntire(line)?.let { mr ->
            val x = mr.groupValues[1].toInt()
            val y = mr.groupValues[2].toInt()
            p.swap(x, y)
        }
        partner.matchEntire(line)?.let { mr ->
            val x = mr.groupValues[1][0]
            val y = mr.groupValues[2][0]
            p.swap(p.indexOf(x), p.indexOf(y))
        }
    }
    return p.joinToString("")
}

fun dance2(programs: String, moves: List<String>): String {
    val (list, cycles) = findDanceCycle(programs, moves)!!
    return list[1_000_000_000 % cycles]
}

fun findDanceCycle(programs: String, moves: List<String>): Pair<List<String>, Int>? {
    val list = mutableListOf(programs)
    repeat(1_000_000_000) {
        list += dance(list.last(), moves)
        if (list.last() == list.first())
            return Pair(list, list.size-1)
    }
    return null
}

fun day16() {
    val input = File("day16.txt").readText().trim()
    val programs = "abcdefghijklmnop"
    val lines = input.split(",")
    println("Day 16 Part 1: ${dance(programs, lines)}")
    println("Day 16 Part 2: ${dance2(programs, lines)}")
}