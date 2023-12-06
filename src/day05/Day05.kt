package day05

import println
import readInput

fun main() {
    val inputs = readInput("day05/Day05")

    fun getMapped(inputs: List<String>, first: List<Long>, endIndex: Int): List<Long> {
        val second = mutableListOf<Long>()
        second.addAll(first)
        inputs.subList(0, endIndex).forEach { ss ->
            val list = ss.split(" ").map { it.toLong() }
            first.forEachIndexed { index, i ->
                val seedList = list[1] until list[1]+list[2]
                if (i in seedList) {
                    val seedIndex = seedList.indexOf(i)
                    second[index] = (list[0] until list[0]+list[2]).elementAt(seedIndex)
                }
            }
        }
        return second
    }

    fun getLocation(inputs: List<String>, first: List<Long>, endIndex: Int): List<Long> {
        val second = mutableListOf<Long>()
        second.addAll(first)
        inputs.subList(0, endIndex).forEach { ss ->
            val list = ss.split(" ").map { it.toLong() }
            first.forEachIndexed { index, i ->
                if (i >= list[1] && i < (list[1] + list[2])) {
                    val secondIndex = i - list[1]
                    second[index] = list[0] + secondIndex
                }
            }
        }
        return second
    }

    fun part1(inputs: List<String>): Long {
        var seeds = inputs[0].replace("seeds: ", "").split(" ").map { it.toLong() }

        var input = inputs
        var endIndex = 1
        repeat(7) { n ->
            input = input.drop(endIndex + 2)
            endIndex = if (n == 6) input.lastIndex else input.indexOfFirst { it.isBlank() }
            seeds = getMapped(input, seeds, endIndex)
        }
        return seeds.min()
    }

    fun part2(inputs: List<String>): Long {
        var seeds = mutableListOf<Long>()
        inputs[0].replace("seeds: ", "").split(" ").map { it.toLong() }.also {
            (it[0] until it[0] + it[1]).forEach { n ->
                seeds.add(n)
            }
            (it[2] until it[2] + it[3]).forEach { n ->
                seeds.add(n)
            }
        }

        var input = inputs
        var endIndex = 1
        repeat(7) { n ->
            input = input.drop(endIndex + 2)
            endIndex = if (n == 6) input.lastIndex else input.indexOfFirst { it.isBlank() }
            seeds = getLocation(input, seeds, endIndex).toMutableList()
        }
        return seeds.min()
    }

    part1(inputs).println()
    part2(inputs).println()
}
