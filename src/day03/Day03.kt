package day03

import println
import readInput

fun main() {
    val inputs = readInput("day03/Day03")

    val symbols = mutableListOf<SymbolPosition>()
    val numbers = mutableListOf<NumberPosition>()
    inputs.forEachIndexed { index, s ->
        s.mapIndexed { colIndex, c ->
            if (!c.isDigit() && c != '.') {
                symbols.add(SymbolPosition(index, colIndex, c))
            }
        }
        numbers.addAll(
            DIGIT_REGEX.findAll(s).map {
                NumberPosition(index, it.range.first, it.value.toInt())
            }
        )
    }

    fun part1(): Int {
        return symbols.map { symbolPosition ->
            val (row, col, _) = symbolPosition
            val list = listOf(
                Position(row - 1, col - 1),
                Position(row - 1, col + 1),
                Position(row + 1, col - 1),
                Position(row + 1, col + 1),
                Position(row, col + 1),
                Position(row, col - 1),
                Position(row - 1, col),
                Position(row + 1, col)
            )
                numbers.filter { numberPosition ->
                    numberPosition.numberRange().any {
                        list.contains(it)
                    }
                }
        }.flatten()
            .sumOf { it.num }

    }

    fun part2(): Int {
        return symbols.filter { it.symbol == '*' }.map { symbolPosition ->
            val (row, col, _) = symbolPosition
            val list = listOf(
                Position(row, col + 1),
                Position(row, col - 1),
                Position(row + 1, col),
                Position(row - 1, col),
                Position(row + 1, col + 1),
                Position(row - 1, col + 1),
                Position(row + 1, col - 1),
                Position(row - 1, col - 1)
            )

            numbers.filter { numberPosition ->
                numberPosition.numberRange().any {
                    list.contains(it)
                }
            }
        }.map {
            if (it.size == 2) {
                it[0].num * it[1].num
            } else {
                0
            }
        }.sumOf {
            it
        }
    }

    part1().println()
    part2().println()
}

data class NumberPosition(val row: Int, val col: Int, val num: Int) {
    fun numberRange(): List<Position> {
        return (col..col + (num.toString().length - 1)).map {
            Position(row, it)
        }
    }
}

data class SymbolPosition(val row: Int, val col: Int, val symbol: Char)
data class Position(val row: Int, val col: Int)

private val DIGIT_REGEX = "\\d+".toRegex()
