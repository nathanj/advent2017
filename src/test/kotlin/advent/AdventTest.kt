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
}
