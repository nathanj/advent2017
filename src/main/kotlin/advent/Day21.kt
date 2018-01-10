package advent

import java.io.File

private val start = ".#./...#/###"

data class Rule(val pattern: String,
                val replacement: String) {
    fun matches(square: String): Boolean {
        return true
    }
}

private val ruleRegex = "(.*) => (.*)".toRegex()

fun rotateSquare(input: String): String {
    val grid = input.split('/')

}

fun parseRules(lines: List<String>): List<Rule> {
    return lines.map { line ->
        val m = ruleRegex.matchEntire(line) ?: error("could not parse rule `$line`")
        Rule(m.groupValues[0], m.groupValues[1])
    }
}

fun breakGrid(input: String, n: Int): List<String> {
    val grid = input.split('/')
    val rowsChunk = grid.chunked(n)
    val result = mutableListOf<String>()
    rowsChunk.forEach { chunk ->
        val chunks = chunk[0].length / n
        if (n == 2) {
            repeat(chunks) { i ->
                result.add(chunk[0].chunked(n)[i] + "/" + chunk[1].chunked(n)[i])
            }
        } else if (n == 3) {
            repeat(chunks) { i ->
                result.add(chunk[0].chunked(n)[i] + "/" + chunk[1].chunked(n)[i] + "/" + chunk[2].chunked(n)[i])
            }
        }
    }
    return result
}

fun joinGrid(input: List<String>, n: Int): String {
    val result = mutableListOf<String>()
    var c = 0
    repeat(n) { result.add("") }
    input.forEach {
        val lines = it.split('/')
        if (n == 3) {
            result[result.size - 3] += lines[0]
            result[result.size - 2] += lines[1]
            result[result.size - 1] += lines[2]
        }
        c++
        if (c == n) {
            result[result.size - 3] += "/"
            result[result.size - 2] += "/"
            result[result.size - 1] += "/"
            repeat(n) { result.add("") }
        }
    }
    return result.joinToString("").dropLast(1)
}

fun fractalLoop(input: String, rules: List<Rule>): String {
    val lines = input.split('/')
    val n = when {
        lines.size % 2 == 0 -> 2
        lines.size % 3 == 0 -> 3
        else -> error("bad size")
    }
    val grid = breakGrid(input, n)
    grid.map { square ->
        val matchingRule = rules.first { it.matches(square) }
        matchingRule.replacement
    }
    return joinGrid(grid, n + 1)
}

fun day21() {
    val lines = File("day21.txt").readLines().filter { it.isNotEmpty() }
    //println("Day 21 Part 1: ${dance(programs, lines)}")
    //println("Day 21 Part 2: ${dance2(programs, lines)}")
}