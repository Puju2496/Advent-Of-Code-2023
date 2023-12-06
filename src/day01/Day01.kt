package day01

import println
import readInput

fun main() {
    val input = readInput("day01/Day01")

    fun part1(inputs: List<String>): Int {
        val intList = inputs.map { input ->
            val list = input.filter { it.isDigit() }
            "${list.first()}${list.last()}".toInt()
        }
        val sum = intList.fold(0) { r, t ->
            r + t
        }
        return sum
    }

    val numberStrings = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    fun part2(inputs: List<String>): Int {
        var sum = 0
        inputs.forEach { input ->
            val digitList = input.filter { it.isDigit() }
            val firstDigit = digitList.first()
            val lastDigit = digitList.last()

            val firstDigitIndex = input.indexOf(firstDigit)
            val lastDigitIndex = input.lastIndexOf(lastDigit)

            val containsStrings = mutableMapOf<Int, List<Int>>()
            numberStrings.forEachIndexed { index, it ->
                if (it in input) {
                    val list = mutableListOf<Int>()
                    var checkIndex = input.indexOf(it, ignoreCase = true)
                    list.add(input.indexOf(it, ignoreCase = true))
                    var newInput = input.replaceFirst(it, "")
                    while (it in newInput) {
                        list.add(input.indexOf(it, startIndex = checkIndex + it.length, ignoreCase = true))
                        checkIndex = input.indexOf(it, startIndex = checkIndex + it.length, ignoreCase = true)
                        newInput = newInput.replaceFirst(it, "")
                    }
                    containsStrings[index+1] = list
                }
            }
            val valuesList = mutableListOf<Int>()
            containsStrings.forEach {
                valuesList.addAll(it.value)
            }
            val minString = valuesList.minOrNull()
            val maxString = valuesList.maxOrNull()

            val first = if (minString != null && minString <= firstDigitIndex) containsStrings.filter { it.value.contains(minString) }.keys.first().toString() else  firstDigit.toString()
            val last = if (maxString != null && maxString >= lastDigitIndex) containsStrings.filter { it.value.contains(maxString) }.keys.first().toString() else lastDigit.toString()

            sum += "$first$last".toInt()
        }

        return sum
    }

    part1(input).println()
    part2(input).println()
}
