package day14

import day1.Node

fun main() {
//    doStuff(765071)
    doStuff(51589, true)
    doStuff(1245, true)
    doStuff(92510, true)
    doStuff(59414, true)
    doStuff(765071, true)
}

private fun doStuff(target: Int, part2: Boolean = false) {
    val root = Node.root(3)
    var first = root
    var second = Node(7).also { first.insertAfter(it) }
    var count = 2
    var addedTwo = false
    val targetString = target.toString().padStart(5, '0')

    fun advance() {
        val sum = first.value + second.value
        addedTwo = sum >= 10
        if (addedTwo) {
            root.insertBefore(Node(1))
            count++
        }
        root.insertBefore(Node(sum % 10))
        count++

        first = first.next(first.value + 1)
        second = second.next(second.value + 1)
    }

    while (true) {
        advance()

        if (!part2 && count >= target + 10) {
            val start = root.next(if (addedTwo) -11 else -10)
            println(start.print(until = if (addedTwo) root.previous else root, delimiter = ""))

            return
        }

        if (part2) {
            val start = root.next(if (addedTwo) -11 else -10)
            val output = start.print(start.next(targetString.length), delimiter = "")

            if (output.length != targetString.length) continue

            if (output == targetString) {
                println(root.distanceTo(start))
                return
            }
        }

    }
}
