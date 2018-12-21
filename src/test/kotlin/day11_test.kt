package day11

import org.junit.Test
import kotlin.test.assertEquals

class Day10Test {
    @Test
    fun powerSumSimple() {
        val grid = PowerGrid(
            (1..300).map { (1..300).map { Power(1) } }
        )

        assertEquals(1, grid.powerSum(1, 1, 1).value)
        assertEquals(4, grid.powerSum(1, 1, 2).value)
        assertEquals(4, grid.powerSum(2, 2, 2).value)
        assertEquals(4, grid.powerSum(20, 20, 2).value)
    }

    @Test
    fun power() {
        assertEquals(4, PowerGrid.power(3, 5, 8).value)
    }

    @Test
    fun powerSum() {
        val grid = PowerGrid.create(18)

        assertEquals(29, grid.powerSum(32, 44, 3).value)
    }
}