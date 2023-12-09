package day08

import println
import readInput
import kotlin.math.max

fun main() {
    val inputs = readInput("day08/Day08")

    fun part1(inputs: List<String>): Int {
        val direction = inputs[0]
        var index = 0

        val network = inputs.subList(2, inputs.size).associate { input ->
            val data = input.split("=", "(", ",", ")").map { it.trim() }
            data[0] to setOf(data[2], data[3])
        }

        var start = "AAA"

        var count = 0
        while (start != "ZZZ") {
            val next = when(direction[index]) {
                'L' -> network[start]?.first()
                else -> network[start]?.last()
            }

            index = (index + 1) % direction.length
            start = next.orEmpty()
            count++
        }

        return count
    }

    fun part2(inputs: List<String>): Long {
        val direction = inputs[0]

        val network = inputs.subList(2, inputs.size).associate { input ->
            val data = input.split("=", "(", ",", ")").map { it.trim() }
            data[0] to setOf(data[2], data[3])
        }

        val start = network.keys.filter { it.last() == 'A' }


        return start.map {
            var index = 0
            var dest = it
            var count = 0L

            while (dest.last() != 'Z') {
              val next = when (direction[index]) {
                  'L' -> network[dest]?.first()
                  else -> network[dest]?.last()
              }
                index = (index + 1) % direction.length
                dest = next.orEmpty()
                count++
            }

            count
        }.lcm()
    }

    part1(inputs).println()
    part2(inputs).println()
}

private fun List<Long>.lcm(): Long {
    var lcm = this[0]

    this.subList(1, size).forEach {
        lcm = lcm(lcm, it)
    }

    return lcm
}

private fun lcm(first: Long, second: Long): Long {
    val max = max(first, second)
    val maxLCM = first * second

    var lcm = max

    while (lcm <= maxLCM) {
        if (lcm % first == 0L && lcm % second == 0L) {
            return lcm
        }
        lcm += max
    }
    return maxLCM
}