package day09

import println
import readInput

fun main() {
    val inputs = readInput("day09/Day09")

    fun retrieveNextHistory(reports: List<Int>): Int {
        val historyList = mutableListOf<List<Int>>()
        var next = reports

        while (historyList.isEmpty() || !historyList.last().all { it == 0 }) {
            val list = mutableListOf<Int>()
            (1..next.lastIndex).forEach {
                list.add(next[it] - next[it - 1])
            }
            historyList.add(list)
            next = historyList.last()
        }

        var history = 0
        for (i in historyList.lastIndex - 1 downTo 0) {
            val current = historyList[i].toMutableList()
            history = current.last() + historyList[i + 1].last()
            current.add(history)

            historyList[i] = current
        }
        history += reports.last()

        return history
    }

    fun part1(inputs: List<String>): Int {

        return inputs.sumOf { input ->
            retrieveNextHistory(input.split(" ").map { it.toInt() })
        }
    }

    fun part2(inputs: List<String>): Int {
        return inputs.sumOf { input ->
            retrieveNextHistory(input.split(" ").map { it.toInt() }.reversed())
        }
    }

    part1(inputs).println()
    part2(inputs).println()
}