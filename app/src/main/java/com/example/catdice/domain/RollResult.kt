package com.example.catdice.domain

data class RollResult(
    val dice1: Int,
    val dice2: Int?,
    val total: Int,
    val timestamp: Long = System.currentTimeMillis()
)