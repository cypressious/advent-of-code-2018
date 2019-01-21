package day1

private object O

fun readInput(name: String): String {
    return O::class.java.getResource(name).readText()
}

fun readLines(name: String) = readInput(name).lines()

class Node(val value: Int) {
    lateinit var next: Node
    lateinit var previous: Node

    fun insertAfter(node: Node) {
        node.previous = this
        next.previous = node

        node.next = next
        next = node
    }

    fun insertBefore(node: Node) {
        node.next = this
        previous.next = node

        node.previous = previous
        previous = node
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

    fun print(
        until: Node = this,
        delimiter: String = " ",
        f: (Node) -> String = { it.value.toString() }
    ) = buildString {
        var n = this@Node
        var whitespace = false

        do {
            if (whitespace) append(delimiter)
            whitespace = true

            append(f(n))
            n = n.next
        } while (n != until)
    }

    companion object {
        fun root(value: Int) = Node(value).apply {
            next = this
            previous = this
        }
    }
}