package advent

import java.io.File

/*
--- Day 7: Recursive Circus ---

Wandering further through the circuits of the computer, you come upon a tower
of programs that have gotten themselves into a bit of trouble. A recursive
algorithm has gotten out of hand, and now they're balanced precariously in a
large tower.

One program at the bottom supports the entire tower. It's holding a large disc,
and on the disc are balanced several more sub-towers. At the bottom of these
sub-towers, standing on the bottom disc, are other programs, each holding their
own disc, and so on. At the very tops of these sub-sub-sub-...-towers, many
programs stand simply keeping the disc below them balanced but with no disc of
their own.

You offer to help, but first you need to understand the structure of these
towers. You ask each program to yell out their name, their weight, and (if
they're holding a disc) the names of the programs immediately above them
balancing on that disc. You write this information down (your puzzle input).
Unfortunately, in their panic, they don't do this in an orderly fashion; by the
time you're done, you're not sure which program gave which information.

For example, if your list is the following:

pbga (66)
xhth (57)
ebii (61)
havc (66)
ktlj (57)
fwft (72) -> ktlj, cntj, xhth
qoyq (66)
padx (45) -> pbga, havc, qoyq
tknk (41) -> ugml, padx, fwft
jptl (61)
ugml (68) -> gyxo, ebii, jptl
gyxo (61)
cntj (57)

...then you would be able to recreate the structure of the towers that looks
like this:

                gyxo
              /     
         ugml - ebii
       /      \     
      |         jptl
      |        
      |         pbga
     /        /
tknk --- padx - havc
     \        \
      |         qoyq
      |             
      |         ktlj
       \      /     
         fwft - cntj
              \     
                xhth

In this example, tknk is at the bottom of the tower (the bottom program), and
is holding up ugml, padx, and fwft. Those programs are, in turn, holding up
other programs; in this example, none of those programs are holding up any
other programs, and are all the tops of their own towers. (The actual tower
balancing in front of you is much larger.)

Before you're ready to help them, you need to make sure your information is
correct. What is the name of the bottom program?

--- Part Two ---

The programs explain the situation: they can't get down. Rather, they could get
down, if they weren't expending all of their energy trying to keep the tower
balanced. Apparently, one program has the wrong weight, and until it's fixed,
they're stuck here.

For any program holding a disc, each program standing on that disc forms a
sub-tower. Each of those sub-towers are supposed to be the same weight, or the
disc itself isn't balanced. The weight of a tower is the sum of the weights of
the programs in that tower.

In the example above, this means that for ugml's disc to be balanced, gyxo,
ebii, and jptl must all have the same weight, and they do: 61.

However, for tknk to be balanced, each of the programs standing on its disc and
all programs above it must each match. This means that the following sums must
all be the same:

    ugml + (gyxo + ebii + jptl) = 68 + (61 + 61 + 61) = 251
    padx + (pbga + havc + qoyq) = 45 + (66 + 66 + 66) = 243
    fwft + (ktlj + cntj + xhth) = 72 + (57 + 57 + 57) = 243

As you can see, tknk's disc is unbalanced: ugml's stack is heavier than the
other two. Even though the nodes above ugml are balanced, ugml itself is too
heavy: it needs to be 8 units lighter for its stack to weigh 243 and keep the
towers balanced. If this change were made, its weight would be 60.

Given that exactly one program is the wrong weight, what would its weight need
to be to balance the entire tower?
*/

data class Node(
        val name: String,
        var data: Int,
        val children: List<String>,
        var totalWeight: Int = 0
)

val childNodeRegex = "^(\\w+) \\((\\d+)\\)".toRegex()
val parentNodeRegex = "^(\\w+) \\((\\d+)\\) -> (.+)".toRegex()

fun findBottomProgram(nodes: List<Node>): Node {
    var currentNode = nodes.first()
    while (true) {
        val parent = nodes.find { n -> n.children.contains(currentNode.name) } ?: return currentNode
        currentNode = parent
    }
}

fun createGraph(lines: List<String>): List<Node> {
    val nodes = ArrayList<Node>()
    lines.forEach { line ->
        val mr1 = parentNodeRegex.matchEntire(line)
        if (mr1 != null) {
            nodes.add(Node(name = mr1.groupValues[1],
                    data = mr1.groupValues[2].toInt(),
                    children = mr1.groupValues[3].split(", ")))
        } else {
            val mr = childNodeRegex.matchEntire(line)
            if (mr != null) {
                nodes.add(Node(
                        name = mr.groupValues[1],
                        data = mr.groupValues[2].toInt(),
                        children = arrayListOf()))
            }
        }
    }

    return nodes
}

fun findNode(nodes: List<Node>, name: String) = nodes.find { n -> n.name == name }!!

fun nodeWeight(nodes: List<Node>, node: Node): Int {
    if (node.totalWeight == 0)
        node.totalWeight = node.data +
                node.children
                        .map { name -> findNode(nodes, name) }
                        .map { n -> nodeWeight(nodes, n) }
                        .sum()
    return node.totalWeight
}

fun findUnique(nodes: List<Node>): Node? {
    val sortedWeights = nodes.map { node -> Pair(node, node.totalWeight) }.sortedBy { it.second }
    // Check if the first item is the unique one.
    if (sortedWeights.first().second != sortedWeights.drop(1).first().second)
        return sortedWeights.first().first
    // Check if the last item is the unique one.
    else if (sortedWeights.last().second != sortedWeights.dropLast(1).last().second)
        return sortedWeights.last().first
    else
        return null
}

fun correctWrongWeightHelper(nodes: List<Node>, start: Node): Pair<Boolean, Node> {
    val children = start.children.map { name ->
        val child = findNode(nodes, name)
        nodeWeight(nodes, child)
        child
    }
    val wrong = findUnique(children)
    if (wrong == null) {
        // start is the node with the incorrect weight
        return Pair(true, start)
    } else {
        val (needsCorrection, wrong) = correctWrongWeightHelper(nodes, wrong)
        if (needsCorrection) {
            val correct = children.find { n -> n.name != wrong.name }!!
            val delta = wrong.totalWeight - correct.totalWeight
            wrong.data -= delta
            wrong.totalWeight -= delta
        }
        return Pair(false, wrong)
    }
}

fun correctWrongWeight(nodes: List<Node>, start: Node): Node {
    val (_, wrong) = correctWrongWeightHelper(nodes, start)
    return wrong
}

fun day7() {
    val input = File("day7.txt").readLines()
    val nodes = createGraph(input)
    val start = findBottomProgram(nodes)
    val wrong = correctWrongWeight(nodes, start)
    println("Day  7 Part 1: ${start}")
    println("Day  7 Part 2: ${wrong}")
}
