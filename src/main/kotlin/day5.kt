package day5

import day1.readInput
import java.util.*

fun main() {
    val input = readInput("/day5/1.txt")
    val list = ArrayList(input.toCharArray().toList())

    var minLength = getCollapsedLength(list)
    println(minLength)

    for (c in 'a'..'z') {
        minLength = Math.min(
            minLength,
            getCollapsedLength(list.filterTo(ArrayList()) { it.toLowerCase() != c })
        )
    }

    println(minLength)
}

private fun getCollapsedLength(list: MutableList<Char>): Int {
    var i = 0

    while (i < list.size - 1) {
        val current = list[i]
        val next = list[i + 1]

        if (current != next && current.toUpperCase() == next.toUpperCase()) {
            list.subList(i, i + 2).clear()

            i = Math.max(0, i - 1)

            continue
        }

        i++
    }

    return list.size
}