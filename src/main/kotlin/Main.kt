import kotlin.system.measureTimeMillis

fun timeit(block: () -> Unit) {
    val time = measureTimeMillis(block)
    println("Time taken: $time ms")
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        //timeit { advent.day1() }
        //timeit { advent.day2() }
        //timeit { advent.day3() }
        //timeit { advent.day4() }
        //timeit { advent.day5() }
        //timeit { advent.day6() }
        //timeit { advent.day7() }
        //timeit { advent.day8() }
        //timeit { advent.day9() }
        //timeit { advent.day10() }
        //timeit { advent.day11() }
        //timeit { advent.day12() }
        //timeit { advent.day13() }
        //timeit { advent.day14() }
        //timeit { advent.day15() }
        //timeit { advent.day16() }
        timeit { advent.day17() }
    }
}
