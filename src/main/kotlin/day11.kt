package day11

fun main() {
    printCoordinate(18, 3)
    printCoordinate(42, 3)
    printCoordinate(1718, 3)

    printCoordinateAndSize(18)
    printCoordinateAndSize(42)
    printCoordinateAndSize(1718)
}

private fun printCoordinateAndSize(serial: Int) {
    val grid = PowerGrid.create(serial)

    val (size, pair) = (1..300).map { size ->
        size to grid.getMax(size)
    }.maxBy { it.second.second }!!
    val (coordinate, power) = pair

    println("${coordinate.x+1},${coordinate.y+1},$size=$power")
}

private fun printCoordinate(serial: Int, size: Int) {
    val (coordinate, power) = findCoordinate(serial, size)
    println("${coordinate.x + 1},${coordinate.y + 1}=$power")
}

private fun findCoordinate(serial: Int, size: Int): Pair<Coordinate, Power> {
    val grid = PowerGrid.create(serial)

    return grid.getMax(size)
}


private fun candidates(size: Int): List<Coordinate> {
    return (0..300 - size)
        .flatMap { x ->
            (0..300 - size)
                .map { y -> Coordinate(x, y) }
        }
}

data class Coordinate(val x: Int, val y: Int)

inline class Power(val value: Int) : Comparable<Power> {
    override fun compareTo(other: Power): Int {
        return value.compareTo(other.value)
    }

    operator fun plus(other: Power) = Power(value + other.value)
    operator fun minus(other: Power) = Power(value - other.value)
}

class PowerGrid(private val powers: List<List<Power>>) {

    private val prefixSums =
        (1..300).map { width ->
            (1..300).map { height -> powers.powerSum(0, 0, width, height) }
        }

    operator fun get(x: Int, y: Int) = powers[x][y]

    fun powerSum(x: Int, y: Int, size: Int): Power {
        val yy = y + size - 1
        val xx = x + size - 1

        var result = prefixSums[xx][yy]

        if (x > 0) result -= prefixSums[x - 1][yy]
        if (y > 0) result -= prefixSums[xx][y - 1]

        if (x > 0 && y > 0) result += prefixSums[x - 1][y - 1]

        return result
    }

    fun getMax(size: Int): Pair<Coordinate, Power> {
        val candidates = candidates(size)

        return candidates
            .map { it to powerSum(it.x, it.y, size) }
            .maxBy { it.second }!!
    }

    companion object {
        fun create(serial: Int): PowerGrid {
            return PowerGrid((1..300).map { x ->
                (1..300).map { y -> power(x, y, serial) }
            })
        }

        fun power(x: Int, y: Int, serial: Int): Power {
            val rackId = x + 10
            var power = (rackId * y + serial) * rackId

            power = (power % 1000) / 100

            return Power(power - 5)
        }

        private fun List<List<Power>>.powerSum(
            x: Int,
            y: Int,
            width: Int,
            height: Int
        ): Power {
            return Power(
                (0 until width)
                    .sumBy { dx ->
                        (0 until height).sumBy { dy ->
                            this[x + dx][y + dy].value
                        }
                    })
        }
    }
}





