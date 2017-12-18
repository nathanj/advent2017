package advent

fun spinlock(steps: Int): Int {
    val list = mutableListOf<Int>(0)
    var currentPos = 0
    (1..2017).forEach { i ->
        currentPos = (currentPos + steps) % list.size
        currentPos++
        list.add(currentPos, i)
    }
    return list[list.indexOf(2017) + 1]
}

fun spinlock2(steps: Int): Int {
    val list = IntArray(50_000_000)
    var currentPos = 0
    var size = 1
    list[0] = 0
    (1..50_000_000).forEach { i ->
        currentPos = (currentPos + steps) % size
        currentPos++
        size++
        list[currentPos] = i
    }
    return list[list.indexOf(0) + 1]
}

fun day17() {
    println("Day 17 Part 1: ${spinlock(303)}")
    println("Day 17 Part 2: ${spinlock2(303)}")
}