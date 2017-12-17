package advent

fun seqA(seed: Long) = generateSequence(seed) { it * 16807 % 2147483647 }.drop(1)
fun seqB(seed: Long) = generateSequence(seed) { it * 48271 % 2147483647 }.drop(1)
fun seqA2(seed: Long) = generateSequence(seed) { it * 16807 % 2147483647 }.drop(1).filter { it % 4 == 0L }
fun seqB2(seed: Long) = generateSequence(seed) { it * 48271 % 2147483647 }.drop(1).filter { it % 8 == 0L }

fun doesMatch(i: Long, j: Long) = (i and 0xFFFF) == (j and 0xFFFF)

fun findMatchingPairs(a: Sequence<Long>, b: Sequence<Long>): Int =
        a.zip(b).take(40_000_000).count { (i, j) -> doesMatch(i, j) }

fun findMatchingPairs2(a: Sequence<Long>, b: Sequence<Long>): Int =
        a.zip(b).take(5_000_000).count { (i, j) -> doesMatch(i, j) }

fun day15() {
    val a = seqA(634)
    val b = seqB(301)
    val a2 = seqA2(634)
    val b2 = seqB2(301)
    println("Day 15 Part 1: ${findMatchingPairs(a, b)}")
    println("Day 15 Part 2: ${findMatchingPairs2(a2, b2)}")
}