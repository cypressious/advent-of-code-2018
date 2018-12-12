package day2

import day1.readLines

fun main(args: Array<String>) {
    val lines = readLines("/day2/1.txt")

    checksum(lines)

    findSimilar(lines)
}

private fun findSimilar(lines: List<String>) {
    for (line1 in lines) {
        outer@ for (line2 in lines) {
            if (line1 == line2) continue

            var diffIndex = -1
            for ((i, c1) in line1.withIndex()) {
                val c2 = line2[i]

                if (c1 == c2) continue

                if (diffIndex >= 0) {
                    continue@outer
                }

                diffIndex = i
            }

            for ((i, c) in line1.withIndex()) {
                if (i != diffIndex) print(c)
            }

            println()
            return
        }
    }
}

private fun checksum(lines: List<String>) {
    var has2 = 0
    var has3 = 0

    for (line in lines) {
        val counts = mutableMapOf<Char, Int>()
        for (c in line.toCharArray()) {
            counts.merge(c, 1) { t, u -> t + u }
        }
        if (counts.values.any { it == 2 }) has2++
        if (counts.values.any { it == 3 }) has3++
    }

    println(has2 * has3)
}

