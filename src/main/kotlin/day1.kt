package day1

fun main(args: Array<String>) {
    val values = readLines("/day1/1.txt").map(String::toInt)

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