package day3

import day1.readLines

private const val SIZE = 10_000

fun main(args: Array<String>) {
    val lines = readLines("/day3/1.txt")

    val areas = lines.map {
        val parts = it.split(" @ ", ",", ": ", "x")
        Area(
            parts[0].drop(1).toInt(),
            parts[1].toInt(),
            parts[2].toInt(),
            parts[3].toInt(),
            parts[4].toInt()
        )
    }

    val nonOverlapping = areas.map { it.id }.toMutableSet()

    val sheet = Array(SIZE) { IntArray(SIZE) }

    for (area in areas) {
        for (x in area.xs) {
            for (y in area.ys) {
                val value = sheet[x][y]
                if (value != 0) {
                    nonOverlapping.remove(Math.abs(value))
                    nonOverlapping.remove(area.id)
                    sheet[x][y] = area.id
                } else {
                    sheet[x][y] = -area.id
                }
            }
        }
    }

    var count = 0
    for (x in 0 until SIZE) {
        for (y in 0 until SIZE) {
            if (sheet[x][y] > 1) {
                count++
            }
        }
    }

    println(count)
    println(nonOverlapping.single())
}

data class Area(val id: Int, val x: Int, val y: Int, val width: Int, val height: Int) {
    val xs get() = x until (x + width)
    val ys get() = y until (y + height)
}