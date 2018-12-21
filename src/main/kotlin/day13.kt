package day13

import day1.readLines
import day13.Direction.*
import day13.Tile.*

fun main() {
    handle(readLines("/day13/test1.txt"))
    handle(readLines("/day13/1.txt"))
    handle(readLines("/day13/test2.txt"), true)
    handle(readLines("/day13/1.txt"), true)
}

private fun handle(input: List<String>, removeCrashed: Boolean = false) {
    val map = createMap(input)

    val comparator = compareBy(Cart::y, Cart::x)

    while (true) {

        for (cart in map.carts.sortedWith(comparator)) {
            val crashedInto = cart.move(map)

            if (crashedInto != null) {
                if (removeCrashed) {
                    map.carts.remove(cart)
                    map.carts.remove(crashedInto)
                } else {
                    println(cart)
                    return
                }
            }
        }

        if (map.carts.size == 1) {
            println(map.carts.single())
            return
        }
    }
}

private fun createMap(input: List<String>): Map {
    val width = input.maxBy { it.length }!!.length
    val height = input.size

    val map = Map(width, height)

    for (y in input.indices) {
        for (x in input[y].indices) {
            val c = input[y][x]

            map[x, y] = when (c) {
                '-', '<', '>' -> Horizontal
                '|', '^', 'v' -> Vertical
                '+' -> Intersection
                '\\' -> CurveTLBR
                '/' -> CurveBLTR
                else -> Empty
            }

            when (c) {
                '>' -> Right
                '<' -> Left
                '^' -> Up
                'v' -> Down
                else -> null
            }?.let { map.carts += Cart(it, x, y) }
        }
    }

    return map
}

private class Map(val width: Int, val height: Int) {
    private val array = Array(width) { Array(height) { Empty } }

    val carts = mutableListOf<Cart>()

    operator fun get(x: Int, y: Int) = array[x][y]
    operator fun set(x: Int, y: Int, value: Tile) {
        array[x][y] = value
    }

    override fun toString(): String {
        return "Map(width=$width, height=$height, carts=${carts.joinToString()})"
    }

    @Suppress("unused") fun render() = buildString {
        for (y in 0 until height) {
            for (x in 0 until width) {
                append(
                    carts.firstOrNull { it.x == x && it.y == y }?.dir?.icon
                        ?: this@Map[x, y].icon
                )
            }

            appendln()
        }
    }
}

private enum class IntersectionChoice(val rotation: Int) {
    Left(-1), Straight(0), Right(1)
}

private val intersectionChoices = IntersectionChoice.values()

private class Cart(var dir: Direction, var x: Int, var y: Int) {
    var nextIntersectionChoice = IntersectionChoice.Left

    fun move(map: Map): Cart? {
        x += dir.dx
        y += dir.dy

        check(x in 0 until map.width)
        check(y in 0 until map.height)

        val crashedInto = map.carts.firstOrNull { it != this && it.x == x && it.y == y }

        if (crashedInto != null) {
            //crash
            return crashedInto
        }

        val tile = map[x, y]

        when (tile) {
            CurveBLTR -> dir = when (dir) {
                Up -> Right
                Right -> Up
                Left -> Down
                Down -> Left
            }

            CurveTLBR -> dir = when (dir) {
                Up -> Left
                Right -> Down
                Left -> Up
                Down -> Right
            }

            Intersection -> {
                dir = directions[Math.floorMod(
                    dir.ordinal + nextIntersectionChoice.rotation,
                    directions.size
                )]

                nextIntersectionChoice =
                    intersectionChoices[(nextIntersectionChoice.ordinal + 1) % intersectionChoices.size]
            }

            else -> {
                //do nothing
            }
        }

        return null
    }

    override fun toString(): String {
        return "Cart(dir=$dir, x=$x, y=$y, nextIntersectionChoice=$nextIntersectionChoice)"
    }


}

private enum class Direction(val dx: Int, val dy: Int, val icon: Char) {
    Up(0, -1, '^'),
    Right(1, 0, '>'),
    Down(0, 1, 'v'),
    Left(-1, 0, '<')
}

private val directions = Direction.values()

private enum class Tile(val icon: Char) {
    Empty(' '),
    Vertical('|'),
    Horizontal('-'),
    CurveTLBR('\\'),
    CurveBLTR('/'),
    Intersection('+')
}
