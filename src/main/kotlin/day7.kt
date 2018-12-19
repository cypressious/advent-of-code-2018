package day7

import day1.readLines

private val regex = "Step ([A-Z]) must be finished before step ([A-Z]) can begin.".toRegex()

fun main() {
    val nodes = mutableMapOf<String, Node>()

    fun node(name: String) = nodes.getOrPut(name) { Node(name) }

    for (line in readLines("/day7/1.txt")) {
        val values = regex.matchEntire(line)!!.groupValues
        val from = node(values[1])
        val to = node(values[2])

        from.comesBefore(to)
    }

    val nodesSorted = nodes.values.sortedBy { it.name }

    while (true) {
        val next = nodesSorted
            .firstOrNull { !it.isDone && it.incoming.all(Node::isDone) }

        if (next == null) {
            println()
            return
        }

        print(next.name)
        next.isDone = true
    }
}

private class Node(val name: String) {
    var isDone = false

    val outgoing = mutableSetOf<Node>()
    val incoming = mutableSetOf<Node>()

    fun comesBefore(other: Node) {
        outgoing += other
        other.incoming += this
    }

    override fun toString() = "$name -> ${outgoing.joinToString { it.name }}"
}