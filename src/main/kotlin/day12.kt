package day12

import day1.readLines

fun main() {
    handle(readLines("/day12/test.txt"), 20)
    handle(readLines("/day12/1.txt"), 20)
    handle(readLines("/day12/1.txt"), 50_000_000_000)
}

private fun handle(input: List<String>, generations: Long) {
    var lastState = input[0].substringAfter(": ")
    var state = lastState

    val rules = input.drop(2).map {
        val (pattern, output) = it.split(" => ")
        Rule(pattern, output == "#")
    }

    for (generation in 1..generations) {
        lastState = state
        val padded = ".....$state....."
        val indices = 2 until padded.length - 3
        state = String(
            indices.map { apply(padded, it + 2, rules) }.toCharArray()
        )

        if (state.contains(lastState)) {
            println("Started repeating at generation $generation")

            val currentSum = potSum(state, generation)
            val diff = currentSum - potSum(lastState, generation - 1)
            val generationsToGo = generations-generation

            println("Current sum: $currentSum. Diff: $diff. Final sum: ${currentSum + generationsToGo * diff}")
            return
        }
    }

    println(potSum(state, generations))
}

private fun potSum(state: String, generation: Long) =
    state.withIndex().filter { it.value == '#' }.map { it.index - (2 * generation) }.sum()

private fun apply(state: String, index: Int, rules: List<Rule>): Char {
    val substring = state.substring(index - 3, index + 2)
    check(substring.length == 5)

    val survives = rules.firstOrNull { it.pattern == substring }?.survives ?: false
    return if (survives) '#' else '.'
}

private class Rule(val pattern: String, val survives: Boolean)