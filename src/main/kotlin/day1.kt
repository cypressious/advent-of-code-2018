package day1

fun main(args: Array<String>) {
    val input = readInput("/day1/1.txt")
    val values = input.lines().map(String::toInt)

    println(values.sum())

    val seenValues = mutableSetOf<Int>()
    var current = 0

    while (true) {
        for (value in values) {
            current += value
            if (!seenValues.add(current)) {
                println(current)
                return
            }
        }
    }
}