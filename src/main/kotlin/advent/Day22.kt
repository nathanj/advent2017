package advent

import java.io.File

fun parseVirusGraph(input: String): Array<CharArray> {
    input.lines().let { lines ->
        val padding = 1000
        val size = lines.size + padding * 2
        val grid = Array(size, { CharArray(size, { '.' }) })
        lines.forEachIndexed { i, line ->
            line.forEachIndexed { j, ch ->
                grid[i + padding][j + padding] = ch
            }
        }
        return grid
    }
}

private enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

private fun turnLeft(dir: Direction) = when (dir) {
    Direction.UP -> Direction.LEFT
    Direction.LEFT -> Direction.DOWN
    Direction.DOWN -> Direction.RIGHT
    Direction.RIGHT -> Direction.UP
}

private fun turnRight(dir: Direction) = when (dir) {
    Direction.UP -> Direction.RIGHT
    Direction.LEFT -> Direction.UP
    Direction.DOWN -> Direction.LEFT
    Direction.RIGHT -> Direction.DOWN
}

private fun reverseDir(dir: Direction) = when (dir) {
    Direction.UP -> Direction.DOWN
    Direction.LEFT -> Direction.RIGHT
    Direction.DOWN -> Direction.UP
    Direction.RIGHT -> Direction.LEFT
}

private fun dirToVector(dir: Direction) = when (dir) {
    Direction.UP -> Pair(-1, 0)
    Direction.LEFT -> Pair(0, -1)
    Direction.DOWN -> Pair(1, 0)
    Direction.RIGHT -> Pair(0, 1)
}

fun runVirusGraph(grid: Array<CharArray>, numActions: Int): Int {
    val newGrid = Array(grid.size, { CharArray(grid.size) })
    grid.forEachIndexed { i, line ->
        line.forEachIndexed { j, ch ->
            newGrid[i][j] = ch
        }
    }
    var dir = Direction.UP
    var x = grid.size / 2
    var y = grid.size / 2
    var infected = 0
    repeat(numActions) {
        if (newGrid[y][x] == '.') {
            dir = turnLeft(dir)
            newGrid[y][x] = '#'
            infected++
        } else {
            dir = turnRight(dir)
            newGrid[y][x] = '.'
        }
        dirToVector(dir).let { vec ->
            y += vec.first
            x += vec.second
        }
    }
    return infected
}

fun runVirusGraph2(grid: Array<CharArray>, numActions: Int): Int {
    val newGrid = Array(grid.size, { CharArray(grid.size) })
    grid.forEachIndexed { i, line ->
        line.forEachIndexed { j, ch ->
            newGrid[i][j] = ch
        }
    }
    var dir = Direction.UP
    var x = grid.size / 2
    var y = grid.size / 2
    var infected = 0
    repeat(numActions) {
        when (newGrid[y][x]) {
            '.' -> {
                dir = turnLeft(dir)
                newGrid[y][x] = 'W'
            }
            'W' -> {
                newGrid[y][x] = '#'
                infected++
            }
            '#' -> {
                dir = turnRight(dir)
                newGrid[y][x] = 'F'
            }
            'F' -> {
                dir = reverseDir(dir)
                newGrid[y][x] = '.'
            }
        }
        dirToVector(dir).let { vec ->
            y += vec.first
            x += vec.second
        }
    }
    return infected
}

fun day22() {
    val input = File("day22.txt").readText().trim()
    val grid = parseVirusGraph(input)
    println("Day 22 Part 1: ${runVirusGraph(grid, 10_000)}")
    println("Day 22 Part 2: ${runVirusGraph2(grid, 10_000_000)}")
}