package advent

import java.io.File

private val start = ".#./...#/###"

data class Rule(val pattern: String,
                val replacement: String) {
    fun matches(square: String): Boolean {
        return pattern == square
    }
}

private val ruleRegex = "(.*) => (.*)".toRegex()

fun flipHoriz(input: String): String {
    val grid = input.split('/').map { it.toMutableList() }
    repeat(grid.size) { i ->
        repeat(grid.size / 2) { j ->
            val tmp = grid[i][j]
            grid[i][j] = grid[i][grid.size - 1 - j]
            grid[i][grid.size - 1 - j] = tmp
        }
    }
    return grid.joinToString("/") { it.joinToString("") }
}

fun flipVert(input: String): String {
    val grid = input.split('/').map { it.toMutableList() }
    repeat(grid.size / 2) { i ->
        repeat(grid.size) { j ->
            val tmp = grid[i][j]
            grid[i][j] = grid[grid.size - 1 - i][j]
            grid[grid.size - 1 - i][j] = tmp
        }
    }
    return grid.joinToString("/") { it.joinToString("") }
}

fun rotate(input: String): String {
    val grid = input.split('/').map { it.toMutableList() }
    val grid2 = input.split('/').map { it.toMutableList() }
    repeat(grid.size) { i ->
        repeat(grid.size) { j ->
            grid2[i][j] = grid[grid.size - 1 - j][i]
        }
    }
    return grid2.joinToString("/") { it.joinToString("") }
}

fun parseRules(lines: List<String>): List<Rule> {
    return lines.flatMap { line ->
        val m = ruleRegex.matchEntire(line) ?: error("could not parse rule `$line`")
        val pattern = m.groupValues[1]
        val replacement = m.groupValues[2]
        val r1 = rotate(pattern)
        val r2 = rotate(r1)
        val r3 = rotate(r2)
        val l = listOf(Rule(pattern, replacement),
                Rule(flipHoriz(pattern), replacement),
                Rule(flipVert(pattern), replacement),
                Rule(flipHoriz(flipVert(pattern)), replacement)) +
                listOf(Rule(r1, replacement),
                        Rule(flipHoriz(r1), replacement),
                        Rule(flipVert(r1), replacement),
                        Rule(flipHoriz(flipVert(r1)), replacement)) +
                listOf(Rule(r2, replacement),
                        Rule(flipHoriz(r2), replacement),
                        Rule(flipVert(r2), replacement),
                        Rule(flipHoriz(flipVert(r2)), replacement)) +
                listOf(Rule(r3, replacement),
                        Rule(flipHoriz(r3), replacement),
                        Rule(flipVert(r3), replacement),
                        Rule(flipHoriz(flipVert(r3)), replacement))
        l.distinct()
    }.distinct()
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

fun joinGrid(input: List<String>, n: Int, cols: Int): String {
    val result = mutableListOf<String>()
    var c = 0
    repeat(n) { result.add("") }
    input.forEach {
        val lines = it.split('/')
        when (n) {
            3 -> {
                result[result.size - 3] += lines[0]
                result[result.size - 2] += lines[1]
                result[result.size - 1] += lines[2]
            }
            4 -> {
                result[result.size - 4] += lines[0]
                result[result.size - 3] += lines[1]
                result[result.size - 2] += lines[2]
                result[result.size - 1] += lines[3]
            }
        }
        c++
        if (c == cols) {
            repeat(n) { result.add("") }
            c = 0
        }
    }
    return result.joinToString("/").trimEnd('/')
}

fun fractalLoop(input: String, rules: List<Rule>): String {
    val lines = input.split('/')
    val n = when {
        lines.size % 2 == 0 -> 2
        lines.size % 3 == 0 -> 3
        else -> error("bad size")
    }
    val grid = breakGrid(input, n)
    val modified = grid.map { square ->
        val matchingRule = rules.first { it.matches(square) }
        matchingRule.replacement
    }
    return joinGrid(modified, n + 1, lines[0].length / n)
}

fun countPixels(input: String) = input.count { it == '#' }

fun day21() {
    val lines = File("day21.txt").readLines().filter { it.isNotEmpty() }
    val rules = parseRules(lines)
    val start = ".#./..#/###"
    var tmp = start
    repeat(5) {
        tmp = fractalLoop(tmp, rules)
    }
    println("Day 21 Part 1: ${countPixels(tmp)}")
    repeat(13) {
        tmp = fractalLoop(tmp, rules)
    }
    println("Day 21 Part 2: ${countPixels(tmp)}")
}
