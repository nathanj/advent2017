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
    fun findMatchingDigits() {

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
    }

    @Test
    fun findMatchingDigitsPart2() {
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
}
