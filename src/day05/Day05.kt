package day05

import println
import readInput

fun main() {
    val inputs = readInput("day05/Day05")

    fun getMapped(inputs: List<Pair<LongRange, LongRange>>, first: List<Long>): List<Long> {
        val second = mutableListOf<Long>()
        second.addAll(first)
        inputs.forEach { ss ->
            first.filter { it in ss.second }.map {
                val index = first.indexOf(it)
                second[index] = ss.first.first() + (it - ss.second.first())
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
        val list = inputs.map { input ->
            input.filter { !it.isLetter() && !SPECIAL_CHARACTER.matches(it.toString()) }.trim().split(" ").map { it.toLongOrNull() ?: -1 }
        }.mapIndexed { index, longs ->
            when {
                index != 0 && longs.size > 1 -> {
                    Pair(LongRange(longs[0], longs[0] + longs[2]), LongRange(longs[1], longs[1] + longs[2]))
                }
                index == 0 -> {
                   longs
                }
                else -> {
                    -1
                }
            }
        }
        val endIndexes = list.mapIndexedNotNull { index, l -> index.takeIf { l == -1 } }

        var endIndex = 1
        var seeds = list[0] as List<Long>

        repeat(7) {
            val startIndex = (endIndexes[endIndex] + 1).takeIf { it <= list.lastIndex } ?: list.lastIndex
            val nextEndIndex = (endIndex + 2).takeIf { it <= list.lastIndex } ?: list.lastIndex
            val subEndIndex = if(nextEndIndex <= endIndexes.lastIndex) endIndexes[nextEndIndex] - 1 else list.lastIndex
            seeds = getMapped(list.subList(startIndex, subEndIndex) as List<Pair<LongRange, LongRange>>, seeds)
            endIndex = nextEndIndex
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

private val SPECIAL_CHARACTER = ("[!\"#$%&'()*+.,-/:;\\\\<=>?@\\[\\]^_`{|}~]").toRegex()

