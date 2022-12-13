package day11

import readFileForDay
import java.lang.IllegalArgumentException

fun main() {

    val lines = readFileForDay(11).toMutableList()

    val monkeys = lines.toMonkeys2()

    val accumulatedDivider = monkeys.values.fold(1L) { acc, monkey -> acc * monkey.testDivider }

    repeat(10_000) {
        monkeys.values.forEach { monkey ->
            monkey.takeTurn(monkeys) {
                it.mod(accumulatedDivider)
            }
        }
    }

    println(
        monkeys.values
            .sortedByDescending { it.inspectionCount }
            .take(2)
            .let { (m1, m2) -> m1.inspectionCount * m2.inspectionCount }
    )
}

fun List<String>.toMonkeys2(): Map<Int, Monkey2           > =
    mutableMapOf<Int, Monkey2>().apply {
        windowed(
            size = 7,
            step = 7,
            partialWindows = true,
        ).forEach { rawMonkeyDataLines ->
            val monkeyDataLines = rawMonkeyDataLines.take(6)
            val monkeyId = monkeyDataLines[0].dropLast(1).substringAfter(' ').toInt()
            this[monkeyId] = monkeyDataLines.toMonkey2(monkeyId)
        }
    }

private fun List<String>.toMonkey2(monkeyId: Int) =
    Monkey2(
        id = monkeyId,
        items = this[1].trim()
            .substringAfter(": ")
            .split(", ")
            .map { it.toLong() }
            .toMutableList(),
        operation = this[2].trim().substringAfter("= old ").split(" ")
            .let { (operator, operand) ->
                when (operator) {
                    "+" -> {
                        if (operand == "old") {
                            { currentValue -> currentValue + currentValue }
                        } else {
                            { currentValue -> currentValue + operand.toLong() }
                        }
                    }

                    "*" -> {
                        if (operand == "old") {
                            { currentValue -> currentValue * currentValue }
                        } else {
                            { currentValue -> currentValue * operand.toLong() }
                        }
                    }

                    else -> throw IllegalArgumentException()
                }
            },
        testDivider = this[3].substringAfterLast(' ').toLong(),
        throwTargetIfTrue = this[4].substringAfterLast(' ').toInt(),
        throwTargetIfFalse = this[5].substringAfterLast(' ').toInt(),
    )

data class Monkey2(
    var id: Int,
    private var items: MutableList<Long>,
    private val operation: (Long) -> Long,
    val testDivider: Long,
    private val throwTargetIfTrue: Int,
    private val throwTargetIfFalse: Int,
) {
    var inspectionCount = 0L
        private set

    fun takeTurn(monkeys: Map<Int, Monkey2>, calm: (Long) -> Long) {
        items.forEach { item ->
            val newItemScore = calm(operation(item))
            inspectionCount++
            throwItemToMonkey(
                itemScore = newItemScore,
                monkey = monkeys.getValue(getNextMonkeyNumber(newItemScore)),
            )
        }
        items.clear()
    }

    private fun getNextMonkeyNumber(itemScore: Long): Int =
        if (itemScore.mod(testDivider) == 0L) {
            throwTargetIfTrue
        } else {
            throwTargetIfFalse
        }

    private fun throwItemToMonkey(itemScore: Long, monkey: Monkey2) {
        monkey.receiveItem(itemScore)
    }

    private fun receiveItem(item: Long) {
        items += item
    }
}
