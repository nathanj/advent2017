package day1

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class Day1KtTest {
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
        assertEquals(day1.findMatchingDigits("1122"), listOf(1, 2))
        assertEquals(day1.getCaptchaSolution("1122"), 3)
        assertEquals(day1.findMatchingDigits("1111"), listOf(1, 1, 1, 1))
        assertEquals(day1.getCaptchaSolution("1111"), 4)
        assertEquals(day1.findMatchingDigits("1234"), listOf<Int>())
        assertEquals(day1.getCaptchaSolution("1234"), 0)
        assertEquals(day1.findMatchingDigits("91212129"), listOf(9))
        assertEquals(day1.getCaptchaSolution("91212129"), 9)
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
        assertEquals(day1.findMatchingDigitsPart2("1212"), listOf(1, 2, 1, 2))
        assertEquals(day1.findMatchingDigitsPart2("1221"), listOf<Int>())
        assertEquals(day1.findMatchingDigitsPart2("123425"), listOf(2, 2))
        assertEquals(day1.findMatchingDigitsPart2("123123"), listOf(1, 2, 3, 1, 2, 3))
        assertEquals(day1.findMatchingDigitsPart2("12131415"), listOf(1, 1, 1, 1))
    }


}
