package advent

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class AdventTest {
    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun day1() {
        /*
        1122 produces a sum of 3 (1 + 2) because the first digit (1) matches the second digit and the third digit (2) matches the fourth digit.
        1111 produces 4 because each digit (all 1) matches the next.
        1234 produces 0 because no digit matches the next.
        91212129 produces 9 because the only digit that matches the next one is the last digit, 9.
        */
        assertEquals(findMatchingDigits("1122"), listOf(1, 2))
        assertEquals(getCaptchaSolution("1122"), 3)
        assertEquals(findMatchingDigits("1111"), listOf(1, 1, 1, 1))
        assertEquals(getCaptchaSolution("1111"), 4)
        assertEquals(findMatchingDigits("1234"), listOf<Int>())
        assertEquals(getCaptchaSolution("1234"), 0)
        assertEquals(findMatchingDigits("91212129"), listOf(9))
        assertEquals(getCaptchaSolution("91212129"), 9)

        /*
        1212 produces 6: the list contains 4 items, and all four digits match the digit 2 items ahead.
        1221 produces 0, because every comparison is between a 1 and a 2.
        123425 produces 4, because both 2s match each other, but no other digit has a match.
        123123 produces 12.
        12131415 produces 4.
        */
        assertEquals(findMatchingDigitsPart2("1212"), listOf(1, 2, 1, 2))
        assertEquals(findMatchingDigitsPart2("1221"), listOf<Int>())
        assertEquals(findMatchingDigitsPart2("123425"), listOf(2, 2))
        assertEquals(findMatchingDigitsPart2("123123"), listOf(1, 2, 3, 1, 2, 3))
        assertEquals(findMatchingDigitsPart2("12131415"), listOf(1, 1, 1, 1))
    }

    @Test
    fun day2() {
        /*
        5 1 9 5
        7 5 3
        2 4 6 8
        The first row's largest and smallest values are 9 and 1, and their difference is 8.
        The second row's largest and smallest values are 7 and 3, and their difference is 4.
        The third row's difference is 6.
        In this example, the spreadsheet's checksum would be 8 + 4 + 6 = 18.
        */
        assertEquals(calcDifference("5 1 9 5"), 8)
        assertEquals(calcDifference("7 5 3"), 4)
        assertEquals(calcDifference("2 4 6 8"), 6)

        /*
        5 9 2 8
        9 4 7 3
        3 8 6 5
        In the first row, the only two numbers that evenly divide are 8 and 2; the result of this division is 4.
        In the second row, the two numbers are 9 and 3; the result is 3.
        In the third row, the result is 2.
        In this example, the sum of the results would be 4 + 3 + 2 = 9.
        */
        assertEquals(calcDivide("5 9 2 8"), 4)
        assertEquals(calcDivide("9 4 7 3"), 3)
        assertEquals(calcDivide("3 8 6 5"), 2)
    }

    @Test
    fun day3() {
        /*
        Data from square 1 is carried 0 steps, since it's at the access port.
        Data from square 12 is carried 3 steps, such as: down, left, left.
        Data from square 23 is carried only 2 steps: up twice.
        Data from square 1024 must be carried 31 steps.
        */
        assertEquals(0, spiralDistance(1))
        assertEquals(3, spiralDistance(12))
        assertEquals(2, spiralDistance(23))
        assertEquals(31, spiralDistance(1024))
    }

    @Test
    fun day4() {
        /*
        aa bb cc dd ee is valid.
        aa bb cc dd aa is not valid - the word aa appears more than once.
        aa bb cc dd aaa is valid - aa and aaa count as different words.
        */

        assertTrue(isPassphraseValid("aa bb cc dd ee"))
        assertFalse(isPassphraseValid("aa bb cc dd aa"))
        assertTrue(isPassphraseValid("aa bb cc dd aaa"))

        /*
        abcde fghij is a valid passphrase.
        abcde xyz ecdab is not valid - the letters from the third word can be rearranged to form the first word.
        a ab abc abd abf abj is a valid passphrase, because all letters need to be used when forming another word.
        iiii oiii ooii oooi oooo is valid.
        oiii ioii iioi iiio is not valid - any of these words can be rearranged to form any other word.
        */

        assertTrue(isPassphraseValidPart2("abcde fghij"))
        assertFalse(isPassphraseValidPart2("abcde xyz ecdab"))
        assertTrue(isPassphraseValidPart2("a ab abc abd abf abj"))
        assertTrue(isPassphraseValidPart2("iiii oiii ooii oooi oooo"))
        assertFalse(isPassphraseValidPart2("oiii ioii iioi iiio"))
    }

    @Test
    fun day5() {
        assertEquals(5, mazeSteps(intArrayOf(0, 3, 0, 1, -3)))
        assertEquals(10, mazeSteps(intArrayOf(0, 3, 0, 1, -3), part = 2))
    }

    @Test
    fun day6() {
        assertEquals(5, memCycles(intArrayOf(0, 2, 7, 0)))
        assertEquals(4, memCycles(intArrayOf(0, 2, 7, 0), part = 2))
    }

    @Test
    fun day8() {
        val lines = """b inc 5 if a > 1
a inc 1 if b < 5
c dec -10 if a >= 1
c inc -20 if c == 10""".lines()
        val registers = HashMap<String, Int>()
        val maxEver = runCommands(registers, lines)
        assertEquals(1, registers.maxBy { it.value }!!.value)
        assertEquals(10, maxEver)
    }

    @Test
    fun day9() {
        val tests = mapOf(
                "{}" to 1,
                "{{{}}}" to 6,
                "{{},{}}" to 5,
                "{{{},{},{{}}}}" to 16,
                "{<a>,<a>,<a>,<a>}" to 1,
                "{{<ab>},{<ab>},{<ab>},{<ab>}}" to 9,
                "{{<!!>},{<!!>},{<!!>},{<!!>}}" to 9,
                "{{<a!>},{<a!>},{<a!>},{<ab>}}" to 3
        )

        tests.forEach { str, exp ->
            assertEquals(exp, scoreStream(str).first)
        }

        val tests2 = mapOf(
                "<>" to 0,
                "<random characters>" to 17,
                "<<<<>" to 3,
                "<{!>}>" to 2,
                "<!!>" to 0,
                "<!!!>>" to 0,
                "<{o\"i!a,<{i<a>" to 10
        )

        tests2.forEach { str, exp ->
            assertEquals(exp, scoreStream(str).second)
        }
    }

    @Test
    fun day10() {
        val list = (0..4).map { it }.toMutableList()
        val lengths = listOf(3, 4, 1, 5)
        assertEquals(12, knotHash(list, lengths))

        val tests = mapOf(
                "" to "a2582a3a0e66e6e86e3812dcb672a272",
                "AoC 2017" to "33efeb34ea91902bb2f59c9920caa6cd",
                "1,2,3" to "3efbe78a8d82f29979031a4aa0b16a9d",
                "1,2,4" to "63960835bcdc130f0b66d7ff4f6a5a8e"
        )

        tests.forEach { str, exp ->
            assertEquals(exp, fullKnotHash(str))
        }
    }

    @Test
    fun day11() {
        val tests = mapOf(
                "ne,ne,ne" to 3,
                "ne,ne,sw,sw" to 0,
                "ne,ne,s,s" to 2,
                "se,sw,se,sw,sw" to 3
        )

        for ((str, exp) in tests) {
            assertEquals(exp, findHexDistance(str).first)
        }
    }

    @Test
    fun day12() {
        val lines = """0 <-> 2
1 <-> 1
2 <-> 0, 3, 4
3 <-> 2, 4
4 <-> 2, 3, 6
5 <-> 6
6 <-> 4, 5""".lines()
        val graph = buildPlumberGraph(lines)
        assertEquals(6, programsConnected(graph, "0").size)
        assertEquals(2, findProgramGroups(graph).size)
    }

    @Test
    fun day13() {
        val lines = """0: 3
1: 2
4: 4
6: 4""".lines()
        assertEquals(24, ridePacket(parseFirewall(lines)).second)
        assertEquals(10, delayPacket(lines))
    }

    @Test
    fun day14() {
        val input = "flqrgnkx"
        assertEquals(8108, knotSquaresCount(input))
        assertEquals(1242, knotSquaresRegionsCount(input))
    }

    @Test
    fun day15() {
        val a = seqA(65)
        val b = seqB(8921)
        assertEquals(588, findMatchingPairs(a, b))
        val a2 = seqA2(65)
        val b2 = seqB2(8921)
        assertEquals(309, findMatchingPairs2(a2, b2))
    }

    @Test
    fun day16() {
        val programs = "abcde"
        val lines = """s1
x3/4
pe/b""".lines()
        assertEquals("baedc", dance(programs, lines))
    }

    @Test
    fun day17() {
        assertEquals(638, spinlock(3))
    }

    @Test
    fun day18() {
        val lines = """set a 1
add a 2
mul a a
mod a 5
snd a
set a 0
rcv a
jgz a -1
set a 1
jgz a -2""".lines()
        assertEquals(4, duet(lines))
    }

    @Test
    fun day19() {
        val input = """
        |
        |  +--+
        A  |  C
    F---|----E|--+
        |  |  |  D
        +B-+  +--+"""
        val grid = makeDiagram(input)
        //printDiagram(grid)
        assertEquals(Pair("ABCDEF", 38), runDiagram(grid))
    }

    @Test
    fun day20() {
        val lines = """p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>
p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>""".lines()
        val particles = parseParticles(lines)
        assertEquals(0, adjustParticles(particles))

        val lines2 = """p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>
p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>
p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>
p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>""".lines()
        val particles2 = parseParticles(lines2)
        assertEquals(1, adjustParticlesCollision(particles2))
    }

    @Test
    fun day22() {
        val input = """
            |..#
            |#..
            |...
            """.trimMargin()
        val grid = parseVirusGraph(input)
        assertEquals(5, runVirusGraph(grid, 7))
        assertEquals(41, runVirusGraph(grid, 70))
        assertEquals(5587, runVirusGraph(grid, 10_000))
        assertEquals(26, runVirusGraph2(grid, 100))
        assertEquals(2511944, runVirusGraph2(grid, 10_000_000))
    }

    @Test
    fun day24() {
        val components = """
            |0/2
            |2/2
            |2/3
            |3/4
            |3/5
            |0/1
            |10/1
            |9/10
            """.trimMargin().lines().map { parseComponent(it) }
        val bridges = makeAllBridges(components)
        assertEquals(31, findStrongestBridge(bridges)?.strength)
        assertEquals(19, findStrongestBridge(bridges, part = 2)?.strength)
    }

}
