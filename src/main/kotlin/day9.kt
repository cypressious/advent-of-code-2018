package day9

fun main() {
    play(9, 25)
    play(10, 1618)
    play(13, 7999)
    play(17, 1104)
    play(21, 6111)
    play(30, 5807)
    play(432, 71019)
    play(432, 71019 * 100)
}

private fun play(countPlayers: Int, lastMarble: Int) {
    val scores = LongArray(countPlayers)
    var current = Node(0).also {
        it.next = it
        it.previous = it
    }

    for (value in 1..lastMarble) {
        if (value % 23 != 0) {
            current.next(1).insertAfter(Node(value).also { current = it })
        } else {
            val removed = current.next(-7).remove()
            current = removed.next

            val player = (value - 1) % countPlayers

            scores[player] += value.toLong()
            scores[player] += removed.value.toLong()
        }

    }

    println(scores.max()!!)
}

private class Node(val value: Int) {
    lateinit var next: Node
    lateinit var previous: Node

    fun insertAfter(node: Node) {
        node.previous = this
        next.previous = node

        node.next = next
        next = node
    }

    fun remove() = apply {
        previous.next = next
        next.previous = previous
    }

    fun next(i: Int): Node {
        var result = this

        repeat(Math.abs(i)) {
            result = if (i > 0) result.next else result.previous
        }

        return result
    }
}