package advent

import java.io.File

class Component(val left: Int, val right: Int) {
    override fun toString(): String {
        return "$left/$right"
    }

    val strength get() = left + right
}

val List<Component>.strength get() = this.sumBy { c -> c.strength }

fun parseComponent(input: String): Component {
    val nums = input.split('/')
    return Component(nums[0].toInt(), nums[1].toInt())
}

fun makeAllBridges(components: List<Component>): List<List<Component>> {
    fun helper(components: List<Component>, bridge: List<Component>, port: Int): List<List<Component>> {
        val result = components.filter { it.left == port }.flatMap { c ->
            helper(components.filter { it != c }, bridge.plus(c), c.right)
        }
        val result2 = components.filter { it.right == port }.flatMap { c ->
            helper(components.filter { it != c }, bridge.plus(c), c.left)
        }
        return listOf(bridge) + result + result2
    }

    return helper(components, ArrayList(), 0)
}

fun findStrongestBridge(bridges: List<List<Component>>, part: Int = 1): List<Component>? {
    return if (part == 1) {
        bridges
    } else {
        val maxSize = bridges.map { it.size }.max()
        bridges.filter { bridge -> bridge.size == maxSize }
    }.maxBy { bridge -> bridge.strength }
}

fun day24() {
    val components = File("day24.txt").readLines().map { line -> parseComponent(line) }
    val bridges = makeAllBridges(components)
    println("Day 24 Part 1: ${findStrongestBridge(bridges)?.strength}")
    println("Day 24 Part 2: ${findStrongestBridge(bridges, part = 2)?.strength}")
}
