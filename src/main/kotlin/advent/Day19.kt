package advent

import java.io.File

typealias Diagram = Array<Array<Char>>

fun makeDiagram(str: String): Diagram {
    val lines = str.lines().filter { it.isNotEmpty() }
    val maxLine = lines.map { it.length }.max()!!
    return Array(lines.size, { y -> Array(maxLine, { x -> lines[y].getOrElse(x, { ' ' }) }) })
}

fun printDiagram(grid: Diagram) {
    for (row in grid) {
        print(">")
        for (ch in row) {
            print(ch)
        }
        println()
    }
}

fun findDiagramStart(grid: Diagram) = grid[0].indexOf('|')

fun findNewDirection(grid: Diagram, x: Int, y: Int, dirX: Int, dirY: Int): Pair<Int, Int> {
    val neighbors = listOf(Pair(-1, 0), Pair(1, 0), Pair(0, 1), Pair(0, -1))
    for ((j, i) in neighbors) {
        if (i != dirX && j != dirY) {
            try {
                if (grid[y + j][x + i] != ' ') {
                    return Pair(j, i)
                }
            } catch (ex: ArrayIndexOutOfBoundsException) {

            }
        }
    }
    throw RuntimeException("did not find neighbor, x=$x y=$y")
}

fun runDiagram(grid: Diagram): Pair<String, Int> {
    var currentX = findDiagramStart(grid)
    var currentY = 0
    var dirX = 0
    var dirY = 1
    var res = ""
    var steps = 0

    while (true) {
        currentX += dirX
        currentY += dirY
        steps++

        when (grid[currentY][currentX]) {
            ' ' -> return Pair(res, steps)
            in 'A'..'Z' -> res += grid[currentY][currentX]
            '|', '-' -> {
                // nothing, continue on
            }
            '+' -> {
                val newDir = findNewDirection(grid, currentX, currentY, dirX, dirY)
                dirY = newDir.first
                dirX = newDir.second
            }
        }
    }
}

fun day19() {
    val grid = makeDiagram(File("day19.txt").readText())
    //printDiagram(grid)
    val answer = runDiagram(grid)
    println("Day 19 Part 1: ${answer.first}")
    println("Day 19 Part 2: ${answer.second}")
}