/*
--- Day 3: Spiral Memory ---

You come across an experimental new kind of memory stored on an infinite
two-dimensional grid.

Each square on the grid is allocated in a spiral pattern starting at a location
marked 1 and then counting up while spiraling outward. For example, the first
few squares are allocated like this:

17  16  15  14  13
18   5   4   3  12
19   6   1   2  11
20   7   8   9  10
21  22  23---> ...

While this is very space-efficient (no squares are skipped), requested data
must be carried back to square 1 (the location of the only access port for this
memory system) by programs that can only move up, down, left, or right. They
always take the shortest path: the Manhattan Distance between the location of
the data and square 1.

For example:

Data from square 1 is carried 0 steps, since it's at the access port.
Data from square 12 is carried 3 steps, such as: down, left, left.
Data from square 23 is carried only 2 steps: up twice.
Data from square 1024 must be carried 31 steps.
How many steps are required to carry the data from the square identified in your puzzle input all the way to the access port?

Your puzzle input is 325489.

--- Part Two ---

As a stress test on the system, the programs here clear the grid and then store
the value 1 in square 1. Then, in the same allocation order as shown above,
they store the sum of the values in all adjacent squares, including diagonals.

So, the first few squares' values are chosen as follows:

Square 1 starts with the value 1.
Square 2 has only one adjacent filled square (with value 1), so it also stores 1.
Square 3 has both of the above squares as neighbors and stores the sum of their values, 2.
Square 4 has all three of the aforementioned squares as neighbors and stores the sum of their values, 4.
Square 5 only has the first and fourth squares as neighbors, so it gets the value 5.
Once a square is written, its value does not change. Therefore, the first few squares would receive the following values:

147  142  133  122   59
304    5    4    2   57
330   10    1    1   54
351   11   23   25   26
362  747  806--->   ...

What is the first value written that is larger than your puzzle input?
*/

package advent

private val input = 325489

// Returns (bottom right value of each ring in the spiral, length of that
// spiral ring).
fun spiral(): Sequence<Pair<Int, Int>> {
    return generateSequence(Pair(1, 0), {
        Pair(it.first + it.second + 8, it.second + 8)
    })
}

fun switchDir(p: Pair<Int, Int>): Pair<Int, Int> = when (p) {
    Pair(-1, 0) -> Pair(0, -1)
    Pair(0, -1) -> Pair(1, 0)
    Pair(1, 0) -> Pair(0, 1)
    else -> Pair(0, 0)
}

fun spiralDistance(n: Int): Int {
    spiral().forEachIndexed { index, (l, len) ->
    if (n <= l && len == 0)
        return 0
    if (n <= l) {
        var loc = Pair(index, index)
        var current = l
        var dir = Pair(-1, 0) // start left
        val perSide = len / 4
        var side = 0
        while (current != n) {
            loc = Pair(loc.first + dir.first, loc.second + dir.second)
            current--
            side++
            if (side == perSide) {
                side = 0
                dir = switchDir(dir)
            }
        }
        return Math.abs(loc.first) + Math.abs(loc.second)
    }
}
return 0
}

fun spiralPart2(input: Int): Int {
    var x = 1
    var y = 0
    var board = Array(1000, { IntArray(1000) })
    var dir = Pair(1, 0)

    board[0 + 500][0 + 500] = 1

    while (true) {
        board[x + 500][y + 500] =
            board[x - 1 + 500][y - 1 + 500] +
            board[x - 1 + 500][y - 0 + 500] +
            board[x - 1 + 500][y + 1 + 500] +
            board[x  + 500][y - 1 + 500] +
            board[x  + 500][y - 0 + 500] +
            board[x  + 500][y + 1 + 500] +
            board[x + 1 + 500][y - 1 + 500] +
            board[x + 1 + 500][y - 0 + 500] +
            board[x + 1 + 500][y + 1 + 500]

        if (board[x+500][y+500] > input)
            return board[x+500][y+500]

        when (dir) {
            // turn right
            Pair(0, 1) -> if (board[x + 1 + 500][y + 500] == 0) dir = Pair(1, 0)
            // turn up
            Pair(1, 0) -> if (board[x + 500][y - 1 + 500] == 0) dir = Pair(0, -1)
            // turn left
            Pair(0, -1) -> if (board[x - 1 + 500][y + 500] == 0) dir = Pair(-1, 0)
            // turn down
            Pair(-1, 0) -> if (board[x + 500][y + 1 + 500] == 0) dir = Pair(0, 1)
        }

        x += dir.first
        y += dir.second
    }
}

fun day3() {
    println("Day  3 Part 1: " + spiralDistance(input))
    println("Day  3 Part 2: " + spiralPart2(input))
}
