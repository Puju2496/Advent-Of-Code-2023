import java.io.File

fun main() {
    val inputs = File(
        "src",
        "Day02.txt"
    ).readLines()

    println(part1(inputs))
    println(part2(inputs))
}

fun part1(inputs: List<String>): Int {
    var idsSum = 0
    inputs.forEach {
        var shouldID = true
        val input = it.split(":", ";")
        val id = input[0].split(" ")[1].toInt()

        input.subList(1, input.size).forEach loop@ { subInput ->
            val childMap = mutableMapOf<String, Int>()

            val next = subInput.split(", ")
            next.forEach { nextSub ->
                val new = nextSub.trim().split(" ")
                val color = new[1]
                val amount = new[0].toInt()
                childMap[color] = amount
            }
            if ((childMap[BLUE] ?: 0) > blue || (childMap[RED] ?: 0) > red || (childMap[GREEN] ?: 0) > green) {
                shouldID = false
                return@loop
            }
        }

        if (shouldID) {
            idsSum += id
        }
    }
    return idsSum
}

fun part2(inputs: List<String>): Int {
    var sum = 0

    inputs.forEach { i ->
        var input = i.split(":", ";")
        input = input.subList(1, input.size)

        val colorMap = mutableMapOf<String, Int>()
        input.forEach { d ->
            val data = d.split(",")
            data.forEach {
                val new = it.trim().split(" ")
                colorMap[new[1]] = maxOf(new[0].toInt(), colorMap[new[1]] ?: 0)
            }
        }
        val product = colorMap.values.fold(1) { t, r ->
            t * r
        }
        sum += product
    }
    return sum
}

private const val BLUE = "blue"
private const val RED = "red"
private const val GREEN = "green"

private const val blue = 14
private const val red = 12
private const val green = 13
