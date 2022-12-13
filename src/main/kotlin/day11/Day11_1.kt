package day11

import readFileForDay
import java.lang.IllegalArgumentException

fun main() {

    val lines = readFileForDay(11).toMutableList()

    val monkeys = lines.toMonkeys()

    repeat(20) {
        monkeys.values.forEach { monkey ->
            monkey.takeTurn(monkeys)
        }
    }

    println(
        monkeys.values
            .sortedByDescending { it.inspectionCount }
            .take(2)
            .map { it.inspectionCount }
            .reduce { acc, current -> acc * current }
    )
}

fun List<String>.toMonkeys(): Map<Int, Monkey> =
    mutableMapOf<Int, Monkey>().apply {
        windowed(
            size = 7,
            step = 7,
            partialWindows = true,
        ).forEach { rawMonkeyDataLines ->
            val monkeyDataLines = rawMonkeyDataLines.take(6)
            val monkeyId = monkeyDataLines[0].dropLast(1).substringAfter(' ').toInt()
            this[monkeyId] = monkeyDataLines.toMonkey(monkeyId)
        }
    }

private fun List<String>.toMonkey(monkeyId: Int) =
    Monkey(
        id = monkeyId,
        items = this[1].trim()
            .substringAfter(": ")
            .split(", ")
            .map { it.toInt() }
            .toMutableList(),
        operation = this[2].trim().substringAfter("= old ").split(" ")
            .let { (operator, operand) ->
                when (operator) {
                    "+" -> {
                        if (operand == "old") {
                            { currentValue -> currentValue + currentValue }
                        } else {
                            { currentValue -> currentValue + operand.toInt() }
                        }
                    }

                    "*" -> {
                        if (operand == "old") {
                            { currentValue -> currentValue * currentValue }
                        } else {
                            { currentValue -> currentValue * operand.toInt() }
                        }
                    }

                    else -> throw IllegalArgumentException()
                }
            },
        test = this[3].substringAfterLast(' ').let {
            { currentValue -> currentValue.mod(it.toInt()) == 0 }
        },
        throwTargetIfTrue = this[4].substringAfterLast(' ').toInt(),
        throwTargetIfFalse = this[5].substringAfterLast(' ').toInt(),
    )

data class Monkey(
    private var id: Int,
    private var items: MutableList<Int>,
    private val operation: (Int) -> Int,
    private val test: (Int) -> Boolean,
    private val throwTargetIfTrue: Int,
    private val throwTargetIfFalse: Int,
) {
    var inspectionCount = 0
        private set

    fun takeTurn(monkeys: Map<Int, Monkey>) {
        items.forEach { item ->
            inspectionCount++
            val newItemScore = (operation(item) / 3f).toInt()
            throwItemToMonkey(
                itemScore = newItemScore,
                monkey = monkeys.getValue(getNextMonkeyNumber(newItemScore)),
            )
        }
        items.clear()
    }

    private fun getNextMonkeyNumber(itemScore: Int): Int =
        if (test(itemScore)) {
            throwTargetIfTrue
        } else {
            throwTargetIfFalse
        }

    private fun throwItemToMonkey(itemScore: Int, monkey: Monkey) {
        monkey.receiveItem(itemScore)
    }

    private fun receiveItem(item: Int) {
        items += item
    }
}
