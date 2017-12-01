import kotlin.system.measureTimeMillis

fun timeit(block: () -> Unit) {
	val time = measureTimeMillis(block)
	println("Time taken: $time ms")
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
	    timeit { day1.day1() }
    }
}
