package day1

private object O

fun readInput(name: String): String {
    return O::class.java.getResource(name).readText().trim()
}

fun readLines(name: String) = readInput(name).lines()