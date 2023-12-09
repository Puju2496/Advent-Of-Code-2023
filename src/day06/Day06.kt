package day06

import println
import readInput

fun main() {
    val inputs = readInput("day06/Day06")

    fun part1(inputs: List<String>): Int {
        val timeList = inputs[0].substringAfter(":").split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val distanceList = inputs[1].substringAfter(":").split(" ").filter { it.isNotBlank() }.map { it.toInt() }

        val product = distanceList.indices.map { index ->
            val time = timeList[index]
            val distance = distanceList[index]

            var count = 0

            (2 until time).map {
                val remainingTime = time - it
                val coveredDistance = remainingTime * it
                if (coveredDistance > distance) {
                    count++
                }
            }
            count
        }.reduce { acc, count ->
            acc * count
        }
        return product
    }

    fun part2(inputs: List<String>): Long {
        val time = inputs[0].substringAfter(":").filter { !it.isWhitespace() }.toLong()
        val distance = inputs[1].substringAfter(":").filter { !it.isWhitespace() }.toLong()

        return (2 until time).map {
            val remainingTime = time - it
            val coveredDistance = remainingTime * it

            coveredDistance > distance
        }.fold(0) { acc, b ->
            if (b) acc + 1 else acc
        }
    }

    part1(inputs).println()
    part2(inputs).println()
}
