package day6

import day1.readLines

fun main() {
    val points = readLines("/day6/1.txt")
        .map { line -> line.split(", ").map(String::toInt).let { (x, y) -> Point(x, y) } }

    val width = points.maxBy { it.x }!!.x
    val height = points.maxBy { it.y }!!.y

    // walk around the plane to identify the points that are outside
    val pointsOutside = mutableSetOf<Point>()

    for (x in -1..width) {
        for (y in listOf(-1, height + 1)) {
            pointsOutside += points.minBy { it.distanceTo(x, y) }!!
        }
    }

    for (y in -1..height) {
        for (x in listOf(-1, width + 1)) {
            val nearest = points.minBy { it.distanceTo(x, y) }!!
            pointsOutside += nearest
        }
    }

    val areasByPoint = mutableMapOf<Point, Int>()

    for (x in 0 until width) {
        for (y in 0 until height) {
            val pointsByDistance = points
                .asSequence()
                .map { it to it.distanceTo(x, y) }
                .sortedBy { it.second }
                .toList()

            if (pointsByDistance[0] == pointsByDistance[1]) continue

            areasByPoint.merge(pointsByDistance[0].first, 1, Int::plus)
        }
    }

    val point = areasByPoint.filter { it.key !in pointsOutside }.maxBy { it.value }
    println(point)
}

private var letter = 'A'

private data class Point(val x: Int, val y: Int, val name: Char = letter++) {


    fun distanceTo(x: Int, y: Int) = Math.abs(this.x - x) + Math.abs(this.y - y)
}