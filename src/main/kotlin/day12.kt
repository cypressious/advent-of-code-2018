package day12

import day1.readLines

fun main() {
    handle(readLines("/day12/test.txt"), 20)
    handle(readLines("/day12/1.txt"), 20)
}

private fun handle(input: List<String>, generations: Long) {
    var state = input[0].substringAfter(": ")

    val rules = input.drop(2).map {
        val (pattern, output) = it.split(" => ")
        Rule(pattern, output == "#")
    }

    for (l in 0 until generations) {
        val padded = ".....$state....."
        val indices = 2 until padded.length - 3
        state = String(
            indices.map { apply(padded, it + 2, rules) }.toCharArray()
        )
    }

    println(state.withIndex().filter { it.value == '#' }.map { it.index - (2 * generations) }.sum())
}

private fun apply(state: String, index: Int, rules: List<Rule>): Char {
    val substring = state.substring(index - 3, index + 2)
    check(substring.length == 5)

    val survives = rules.firstOrNull { it.pattern == substring }?.survives ?: false
    return if (survives) '#' else '.'
}

private class Rule(val pattern: String, val survives: Boolean)