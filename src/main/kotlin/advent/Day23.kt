package advent

import java.io.File

fun runCoprocessor(lines: List<String>): Int {
    val registers = HashMap<String, Long>()
    var pc = 0
    var numMultiplies = 0

    fun valOrReg(str: String): Long {
        return str.toLongOrNull() ?: registers.getOrDefault(str, 0)
    }

    while (true) {
        if (pc >= lines.size)
            return numMultiplies
        val words = lines[pc++].split(" ")
        when (words[0]) {
            "set" ->
                registers[words[1]] = valOrReg(words[2])
            "sub" ->
                registers[words[1]] = valOrReg(words[1]) - valOrReg(words[2])
            "mul" -> {
                registers[words[1]] = valOrReg(words[1]) * valOrReg(words[2])
                numMultiplies++
            }
            "jnz" -> {
                if (valOrReg(words[1]) != 0L) {
                    pc--
                    pc += valOrReg(words[2]).toInt()
                }
            }
            else ->
                error("bad instruction ${lines[pc - 1]}")
        }
    }
}

fun isPrime(n: Long): Boolean {
    return (2..n/2).none { n % it == 0L }
}

fun runCoprocessor2(): Long {
    var b = 106500L
    val c = b + 17000L
    var h = 0L
    while (b <= c) {
        if (!isPrime(b))
            h++
        b += 17
    }
    return h
}

fun day23() {
    val lines = File("day23.txt").readLines()
    println("Day 23 Part 1: ${runCoprocessor(lines)}")
    println("Day 23 Part 2: ${runCoprocessor2()}")
}
