package day8

import day1.readInput


fun main() {
    val data = readInput("/day8/1.txt").split(" ").map { it.toInt() }

    var i = 0
    fun parseNode(): Node {
        val childCount = data[i++]
        val metaCount = data[i++]

        val children = (1..childCount).map { parseNode() }
        val metadata = (1..metaCount).map { data[i++] }

        return Node(children, metadata)
    }

    val root = parseNode()

    fun Node.sum(): Int = metadata.sum() + children.sumBy { it.sum() }

    println(root.sum())
}


class Node(val children: List<Node>, val metadata: List<Int>)