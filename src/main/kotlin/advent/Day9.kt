package advent

import java.io.File

/*
*/

fun scoreStream(stream: String): Pair<Int, Int> {
    var groupStack = 0
    var garbageStack = 0
    var skipNext = false
    var score = 0
    var garbageCharacters = 0
    for (c in stream) {
        //println("groupStack=$groupStack garbageStack=$garbageStack")
        if (skipNext) {
            skipNext = false
            continue
        }
        when (c) {
            '{' -> {
                if (garbageStack == 0)
                    groupStack++
                        else
                            garbageCharacters++
            };
            '}' -> {
                if (garbageStack == 0) {
                    score += groupStack
                groupStack--
                } else
                    garbageCharacters++
            };
            '<' -> {
                if (garbageStack == 0)
                    garbageStack++
                        else
                        garbageCharacters++
            };
            '>' -> {
                garbageStack--
            };
            '!' -> {
                skipNext = true
            };
            else -> {
                if (garbageStack > 0)
                    garbageCharacters++
            };
        }
    }
    return Pair(score, garbageCharacters)
}

fun day9() {
    val stream = File("day9.txt").readLines().first()
        val score = scoreStream(stream)
    println("Day  9 Part 1: ${score.first}")
    println("Day  9 Part 2: ${score.second}")
}
