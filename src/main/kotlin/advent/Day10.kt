package advent

import java.io.File

/*
*/

private val input = "227,169,3,166,246,201,0,47,1,255,2,254,96,3,97,144"

fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this' corresponds to the list
    this[index1] = this[index2]
    this[index2] = tmp
}

fun reverse(list: MutableList<Int>, pos: Int, len: Int) {
var spos = pos
var epos = pos + len - 1

while (spos < epos) {
list.swap(spos % list.size, epos % list.size)
spos++
epos--
}

}

fun knotHash(list: MutableList<Int>, lengths: List<Int>): Int {
var skip = 0
var pos = 0

for (len in lengths) {
//println("len=$len pos=$pos list=$list")
reverse(list, pos, len)
pos += len
pos += skip
skip++
}
println("list=$list")
return list.first() * list.drop(1).first()
}

fun knotHash2(list: MutableList<Int>, lengths: List<Int>): MutableList<Int> {
var skip = 0
var pos = 0

for (i in (0..63)) {
for (len in lengths) {
//println("len=$len pos=$pos list=$list")
reverse(list, pos, len)
pos += len
pos += skip
skip++
}
}
println("list=$list")
return list
}

fun convertStringToList(str: String): List<Int> {
val list = ArrayList<Int>()
for (c in str) {
list.add(c.toInt())
}
list.add(17)
list.add(31)
list.add(73)
list.add(47)
list.add(23)

println("list=$list")
return list
}

fun denseHash(list: MutableList<Int>): List<Int> {
        val l = ArrayList<Int>()
        for (i in (0..15)) {
        l.add(list.drop(i*16).take(16).reduce { a, b -> a xor b })
        }
        return l
        }

fun fullKnotHash(str: String): String {
        val list = (0..255).map { it }.toMutableList()
        val lengths = convertStringToList(str)
        println("lengths=$lengths")
        knotHash2(list, lengths)
        val dense = denseHash(list)
        println("dense=$dense")
        val hash = dense.map { String.format("%02x", it) }.joinToString("")
return hash
}

fun day10() {
val list = (0..255).map { it }.toMutableList()
	val lengths = input.split(",").map(String::toInt)
    println("Day 10 Part 1: ${knotHash(list, lengths)}")
    println("Day 10 Part 2: ${fullKnotHash(input)}")
}
