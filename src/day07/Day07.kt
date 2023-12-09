package day07

import println
import readInput

fun main() {
    val inputs = readInput("day07/Day07")

    fun part1(inputs: List<String>): Int {
        val cardsMap = inputs.map { input ->
            val cardData = input.split(" ")
                val card = cardData[0].trim()
                val sequence = card.groupingBy { char -> char }.eachCount()
                val type = when {
                    sequence.size == 1 && sequence.all { it.value == 5 } -> CardTypes.FIVE_KIND
                    sequence.size == 2 && sequence.any { it.value == 4 } && sequence.any { it.value == 1 } -> CardTypes.FOUR_KIND
                    sequence.size == 2 && sequence.any { it.value == 3 } && sequence.any { it.value == 2 } -> CardTypes.FULL_HOUSE
                    sequence.size == 3 && sequence.any { it.value == 3 } && sequence.filter { it.value == 1 }.count() == 2-> CardTypes.THREE_KIND
                    sequence.size == 3 && sequence.filter { it.value == 2 }.count() == 2 && sequence.filter { it.value == 1 }.count() == 1 -> CardTypes.TWO_PAIR
                    sequence.size == 4 && sequence.filter { it.value == 2 }.count() == 1 && sequence.filter { it.value == 1 }.count() == 3 -> CardTypes.ONE_PAIR
                    sequence.size == card.length -> CardTypes.HIGH_CARD
                    else -> CardTypes.UNKNOWN
                }
                Card(card, cardData[1].trim().toInt(), type, label1)
        }

        return cardsMap.sorted().mapIndexed { index, card ->
            card.amount * (index + 1)
        }.sum()

    }

    fun part2(inputs: List<String>): Int {
        val cardsMap = inputs.map { input ->
            val cardData = input.split(" ")
            val card = cardData[0].trim()
            val cardMap = card.groupBy { it }
            val sequence = if (cardMap.all { it.key == 'J' }) {
                card
            } else {
                val maxKey = cardMap.filter { it.key != 'J' }.maxBy { it.value.count() }.key
                card.replace('J', maxKey)
            }.groupingBy { it }.eachCount()

            val type = when {
                sequence.all { it.key == 'J' } -> CardTypes.FIVE_KIND
                sequence.size == 1 && sequence.all { it.value == 5 } -> CardTypes.FIVE_KIND
                sequence.size == 2 && sequence.any { it.value == 4 } && sequence.any { it.value == 1 } -> CardTypes.FOUR_KIND
                sequence.size == 2 && sequence.any { it.value == 3 } && sequence.any { it.value == 2 } -> CardTypes.FULL_HOUSE
                sequence.size == 3 && sequence.any { it.value == 3 } && sequence.filter { it.value == 1 }.count() == 2 -> CardTypes.THREE_KIND
                sequence.size == 3 && sequence.filter { it.value == 2 }.count() == 2 && sequence.filter { it.value == 1 }.count() == 1 -> CardTypes.TWO_PAIR
                sequence.size == 4 && sequence.filter { it.value == 2 }.count() == 1 && sequence.filter { it.value == 1 }.count() == 3 -> CardTypes.ONE_PAIR
                sequence.size == card.length -> CardTypes.HIGH_CARD
                else -> CardTypes.UNKNOWN
            }
            Card(card, cardData[1].trim().toInt(), type, label2)
        }

        return cardsMap.sorted().mapIndexed { index, card ->
            card.amount * (index + 1)
        }.sum()
    }

    part1(inputs).println()
    part2(inputs).println()
}

data class Card(val name: String, val amount: Int, val cardType: CardTypes, val label: List<Char>): Comparable<Card> {

    override fun compareTo(other: Card): Int {
        return when {
            cardType == other.cardType -> {
                val secondCardChars = other.name

                name.forEachIndexed { index, char ->
                    val secondCardChar = secondCardChars[index]
                    if (char != secondCardChar)
                        return label.indexOf(char).compareTo(label.indexOf(secondCardChar))
                }

                return 0
            }
            else -> {
                cardType.priority.compareTo(other.cardType.priority)
            }
        }
    }
}

private val label1 = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
private val label2 = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')

enum class CardTypes(val priority: Int) {
    FIVE_KIND(7),
    FOUR_KIND(6),
    FULL_HOUSE(5),
    THREE_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1),
    UNKNOWN(0)
}
