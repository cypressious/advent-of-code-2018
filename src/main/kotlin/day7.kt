package day7

import day1.readLines

private val regex = "Step ([A-Z]) must be finished before step ([A-Z]) can begin.".toRegex()

fun main() {
    val nodesMap = mutableMapOf<Char, Node>()

    fun node(name: Char) = nodesMap.getOrPut(name) { Node(name) }

    for (line in readLines("/day7/1.txt")) {
        val values = regex.matchEntire(line)!!.groupValues
        val from = node(values[1][0])
        val to = node(values[2][0])

        from.comesBefore(to)
    }

    val nodes = nodesMap.values.sortedBy(Node::name)

    sequential(nodes)
    parallel(nodes)
}

private fun sequential(nodes: List<Node>) {
    val done = mutableSetOf<Node>()

    fun Node.isDone() = this in done
    fun Node.isReady() = !isDone() && incoming.all(Node::isDone)

    while (true) {
        val next = nodes
            .firstOrNull(Node::isReady)

        if (next == null) {
            println()
            return
        }

        print(next.name)
        done += next
    }
}


private fun parallel(nodes: List<Node>) {
    val done = mutableSetOf<Node>()
    val remaining = nodes
        .associateByTo(mutableMapOf(), { it }, { 60 + (it.name - 'A') })

    fun Node.isDone() = this in done
    fun Node.isReady() = !isDone() && incoming.all(Node::isDone)

    var time = 0
    val workers = arrayOfNulls<Node>(5)

    while (nodes.any { !it.isDone() }) {
        workers.forEach { node ->
            node?.let { remaining[node] = remaining[node]!! - 1 }
        }

        for (i in 0..4) {
            val node = workers[i]
            if (node != null && remaining[node]!! < 0) {
                done += node
                workers[i] = null
            }
        }

        for (i in 0..4) {
            if (workers[i] == null) {
                workers[i] = nodes.firstOrNull { it.isReady() && it !in workers }
            }
        }

        if (workers.any { it != null }) {
            time++
        }
    }

    println(time)
}

private data class Node(val name: Char) {
    val incoming: MutableSet<Node> = mutableSetOf()

    fun comesBefore(other: Node) {
        other.incoming += this
    }
}