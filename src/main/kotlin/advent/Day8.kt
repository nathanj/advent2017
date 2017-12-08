package advent

import java.io.File

/*
--- Day 8: I Heard You Like Registers ---

You receive a signal directly from the CPU. Because of your recent assistance
with jump instructions, it would like you to compute the result of a series of
unusual register instructions.

Each instruction consists of several parts: the register to modify, whether to
increase or decrease that register's value, the amount by which to increase or
decrease it, and a condition. If the condition fails, skip the instruction
without modifying the register. The registers all start at 0. The instructions
look like this:

b inc 5 if a > 1
a inc 1 if b < 5
c dec -10 if a >= 1
c inc -20 if c == 10

These instructions would be processed as follows:

    Because a starts at 0, it is not greater than 1, and so b is not modified.
    a is increased by 1 (to 1) because b is less than 5 (it is 0).
    c is decreased by -10 (to 10) because a is now greater than or equal to 1 (it is 1).
    c is increased by -20 (to -10) because c is equal to 10.

After this process, the largest value in any register is 1.

You might also encounter <= (less than or equal to) or != (not equal to).
However, the CPU doesn't have the bandwidth to tell you what all the registers
are named, and leaves that to you to determine.

What is the largest value in any register after completing the instructions in
your puzzle input?

--- Part Two ---

To be safe, the CPU also needs to know the highest value held in any register
during this process so that it can decide how much memory to allocate to these
operations. For example, in the above instructions, the highest value ever held
was 10 (in register c after the third instruction was evaluated).
*/

val expressionRegex = "(\\w+) (inc|dec) (-?\\d+) if (\\w+) (.*) (-?\\d++)".toRegex()

val cmpOperators: Map<String, (Int, Int) -> Boolean> = mapOf(
        ">" to { a, b -> a > b },
        "<" to { a, b -> a < b },
        ">=" to { a, b -> a >= b },
        "<=" to { a, b -> a <= b },
        "==" to { a, b -> a == b },
        "!=" to { a, b -> a != b }
)

val operators: Map<String, (Int, Int) -> Int> = mapOf(
        "inc" to { a, b -> a + b },
        "dec" to { a, b -> a - b }
)

fun runCommands(registers: HashMap<String, Int>, lines: List<String>): Int {
    var max = Int.MIN_VALUE
    for (line in lines) {
        val m = expressionRegex.matchEntire(line)
                ?: throw RuntimeException("could not parse '$line'")
        val reg = m.groupValues[1]
        val op = operators[m.groupValues[2]]!!
        val value = m.groupValues[3].toInt()
        val cmpReg = m.groupValues[4]
        val cmpOp = cmpOperators[m.groupValues[5]]!!
        val cmpValue = m.groupValues[6].toInt()

        if (cmpOp(registers.getOrDefault(cmpReg, 0), cmpValue))
            registers[reg] = op(registers.getOrDefault(reg, 0), value)
        max = Math.max(max, registers.getOrDefault(reg, 0))
    }
    return max
}

fun day8() {
    val lines = File("day8.txt").readLines()
    val registers = HashMap<String, Int>()
    val maxEver = runCommands(registers, lines)
    val max = registers.maxBy { it.value }
    println("Day  8 Part 1: ${max}")
    println("Day  8 Part 2: ${maxEver}")
}
