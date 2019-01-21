package day14

import day1.Node

fun main() {
    doStuff(9)
    doStuff(5)
    doStuff(18)
    doStuff(2018)
    doStuff(765071)
}

private fun doStuff(iterations: Int) {
    val root = Node.root(3)
    var first = root
    var second = Node(7).also { first.insertAfter(it) }
    var count = 2
    var addedTwo: Boolean

    while (true) {
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

        if (count >= iterations + 10) break
    }

    val start = root.next(if (addedTwo) -11 else -10)
    println(start.print(until = if (addedTwo) root.previous else root, delimiter = ""))
}
