package day10

import day1.readLines

private val regex =
    "position=<([ \\-0-9]+),([ \\-0-9]+)> velocity=<([ \\-0-9]+),([ \\-0-9]+)>".toRegex()

fun main() {
    var points = readLines("/day10/1.txt")
        .map {
            val matchEntire = regex.matchEntire(it)!!
            val (_, x, y, dx, dy) = matchEntire.groupValues
            Point(x.number, y.number, dx.number, dy.number)
        }


    while (true) {
        val minX = points.minBy { it.x }!!.x
        val minY = points.minBy { it.y }!!.y
        val maxX = points.maxBy { it.x }!!.x
        val maxY = points.maxBy { it.y }!!.y

        if (maxY - minY < 20) {
            for (y in minY..maxY) {
                for (x in minX..maxX) {
                    print(if (points.any { it.x == x && it.y == y }) "#" else ".")
                }
                println()
            }

            if (readLine() == "q") return
        }

        points = points.map(Point::next)
    }
}

private data class Point(val x: Long, val y: Long, val dx: Long, val dy: Long) {
    fun next() = Point(x + dx, y + dy, dx, dy)
}

private val String.number get() = trim().toLong()