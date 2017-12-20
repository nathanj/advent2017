package advent

import java.io.File

private val parser = "p=<(.*)>, v=<(.*)>, a=<(.*)>".toRegex()

data class Particle(
        val id: Int,
        val p: LongArray,
        val v: LongArray,
        val a: LongArray
) {
    fun update() {
        v[0] += a[0]
        v[1] += a[1]
        v[2] += a[2]
        p[0] += v[0]
        p[1] += v[1]
        p[2] += v[2]
    }

    fun distance() = p.map { Math.abs(it) }.sum()
    fun acceleration() = a.map { Math.abs(it) }.sum()
    fun velocity() = v.map { Math.abs(it) }.sum()
    fun hasCollision(particles: List<Particle>): Boolean {
	return particles.any { other -> id != other.id && collidesWith(other) }
    }
    private fun collidesWith(o: Particle) =
            p[0] == o.p[0] &&
                    p[1] == o.p[1] &&
                    p[2] == o.p[2]
}

fun parseParticles(lines: List<String>): List<Particle> {
    var id = 0
    return lines.map { line ->
        val m = parser.matchEntire(line) ?: throw RuntimeException("could not parse $line")
        val p = m.groupValues[1].split(",").map { it.trim().toLong() }.toLongArray()
        val v = m.groupValues[2].split(",").map { it.trim().toLong() }.toLongArray()
        val a = m.groupValues[3].split(",").map { it.trim().toLong() }.toLongArray()
        Particle(id++, p, v, a)
    }
}

fun adjustParticles(particles: List<Particle>): Int {
    var p = particles
    p = p.filter { it.acceleration() == p.map { it.acceleration() }.min() }
    p = p.filter { it.velocity() == p.map { it.velocity() }.min() }
    p = p.filter { it.distance() == p.map { it.distance() }.min() }
    return p.first().id
}

fun adjustParticlesCollision(particles: List<Particle>): Int {
    var p = particles
    repeat(1000) {
        p.forEach { it.update() }
        p = p.filter { !it.hasCollision(p) }
    }
    return p.size
}

fun day20() {
    val lines = File("day20.txt").readLines().filter { it.isNotEmpty() }
    val particles = parseParticles(lines)
    println("Day 20 Part 1: ${adjustParticles(particles)}")
    println("Day 20 Part 2: ${adjustParticlesCollision(particles)}")
}
