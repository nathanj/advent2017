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
	    timeit { advent.day3() }
    }
}
