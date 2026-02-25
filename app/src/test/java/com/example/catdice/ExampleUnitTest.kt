package com.example.catdice

import com.example.catdice.domain.RollResult
import org.junit.Test
import org.junit.Assert.*

class RollLogicTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun rollResult_calculation_isCorrect() {
        val dice1 = 3
        val dice2 = 4
        val total = dice1 + dice2

        val result = RollResult(dice1, dice2, total)

        assertEquals(7, result.total)
        assertEquals(3, result.dice1)
    }
}