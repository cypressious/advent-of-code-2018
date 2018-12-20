package day9

fun main() {
    play(9, 25, true)
    play(10, 1618)
    play(13, 7999)
    play(17, 1104)
    play(21, 6111)
    play(30, 5807)
    play(432, 71019)
}

private fun play(countPlayers: Int, lastMarble: Int, print: Boolean = false) {
    val marbles = mutableListOf(0)
    val scores = IntArray(countPlayers)
    var currentIndex = 0

    for (value in 1..lastMarble) {
        val player = (value - 1) % countPlayers

        if (value % 23 != 0) {
            currentIndex = (currentIndex + 2) % marbles.size

            if (currentIndex == 0) currentIndex = marbles.size

            marbles.add(currentIndex, value)
        } else {
            scores[player] += value

            currentIndex = Math.floorMod(currentIndex - 7, marbles.size)
            scores[player] += marbles.removeAt(currentIndex)
        }

        if (print) {
            val marblesString = buildString {
                for (i in marbles.indices) {
                    if (i != 0) append(" ")

                    if (i == currentIndex) append("(")
                    append(marbles[i])
                    if (i == currentIndex) append(")")
                }
            }

            println("[${player + 1}] $marblesString")
        }
    }

    println(scores.max()!!)
}
