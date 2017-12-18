package advent

import java.io.File
import java.util.concurrent.*
import kotlin.concurrent.thread

fun duet(lines: List<String>): Long {
    val registers = HashMap<String, Long>()
    var sound = 0L
    var pc = 0

    fun valOrReg(str: String): Long {
        return str.toLongOrNull() ?: registers.getOrDefault(str, 0)
    }

    while (true) {
        val words = lines[pc++].split(" ")
        when (words[0]) {
            "snd" -> {
                sound = valOrReg(words[1])
            }
            "set" -> {
                registers[words[1]] = valOrReg(words[2])
            }
            "add" -> {
                registers[words[1]] = valOrReg(words[1]) + valOrReg(words[2])
            }
            "mul" -> {
                registers[words[1]] = valOrReg(words[1]) * valOrReg(words[2])
            }
            "mod" -> {
                registers[words[1]] = valOrReg(words[1]) % valOrReg(words[2])
            }
            "rcv" -> {
                if (valOrReg(words[1]) != 0L)
                    return sound
            }
            "jgz" -> {
                if (valOrReg(words[1]) > 0L) {
                    pc--
                    pc += valOrReg(words[2]).toInt()
                }
            }
        }
    }
}

fun duet2(lines: List<String>, id: Long, send: BlockingQueue<Long>, recv: BlockingQueue<Long>): Long {
    val registers = HashMap<String, Long>()
    var pc = 0
    var sends = 0L

    registers["p"] = id

    fun valOrReg(str: String): Long {
        return str.toLongOrNull() ?: registers.getOrDefault(str, 0)
    }

    while (true) {
        val words = lines[pc++].split(" ")
        when (words[0]) {
            "snd" -> {
                send.add(valOrReg(words[1]))
                sends++
            }
            "set" -> {
                registers[words[1]] = valOrReg(words[2])
            }
            "add" -> {
                registers[words[1]] = valOrReg(words[1]) + valOrReg(words[2])
            }
            "mul" -> {
                registers[words[1]] = valOrReg(words[1]) * valOrReg(words[2])
            }
            "mod" -> {
                registers[words[1]] = valOrReg(words[1]) % valOrReg(words[2])
            }
            "rcv" -> {
                registers[words[1]] = recv.poll(1L, TimeUnit.SECONDS) ?: return sends
            }
            "jgz" -> {
                if (valOrReg(words[1]) > 0L) {
                    pc--
                    pc += valOrReg(words[2]).toInt()
                }
            }
        }
    }
}

fun day18() {
    val lines = File("day18.txt").readLines()
    println("Day 18 Part 1: ${duet(lines)}")

    val q1 = ArrayBlockingQueue<Long>(100000)
    val q2 = ArrayBlockingQueue<Long>(100000)
    val t1 = thread(start = true) { duet2(lines, 0, q1, q2) }
    val t2 = thread(start = true) {
        val b = duet2(lines, 1, q2, q1);
        println("Day 18 Part 2: ${b}")
    }
    t1.join()
    t2.join()
}
