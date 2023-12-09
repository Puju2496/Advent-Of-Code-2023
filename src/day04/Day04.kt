package day04

import println
import readInput
import kotlin.math.pow

fun main() {
    val inputs = readInput("day04/Day04")

    fun String.getWinningAndCurrentList() = run {
        replaceRange(0, indexOf(":") + 1, "").split("|").map { next ->
            next.split(" ").filter { it.isNotEmpty() }.map {
                it.toInt()
            }
        }
    }

    fun part1(inputs: List<String>): Int {
        return inputs.map { s ->
            val (winning, list) = s.getWinningAndCurrentList()
            winning.count { it in list }

        }.sumOf { 2.0.pow(it - 1).toInt() }
    }

    fun part2(inputs: List<String>): Int {
        val cardMap = mutableMapOf<Int, Int>()
        inputs.mapIndexed { index, s ->
            val (winning, list) = s.getWinningAndCurrentList()
            cardMap[index + 1] = (cardMap[index + 1] ?: 0) + 1
            Pair(index + 1, winning.count { it in list })
        }.forEach {
            val index = it.first
            val count = it.second

            (index + 1..index + count).forEach { i ->
                val current = cardMap[index] ?: 1
                cardMap[i] = (cardMap[i] ?: 1) + current
            }
        }

        return cardMap.map {
            it.value
        }.sumOf { value ->
            value
        }
    }

    part1(inputs).println()
    part2(inputs).println()
}
