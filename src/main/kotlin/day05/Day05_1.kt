package day05

import readFile
import java.util.*

fun main() {
    val lines = readFile("day05_input.txt")
    val indexOfBlankLine = lines.indexOfFirst { it.isBlank() }
    val stacksData = lines.subList(0, indexOfBlankLine - 1).reversed()
    val movesData = lines.subList(indexOfBlankLine + 1, lines.size)

    val stackNumberLine = lines.subList(indexOfBlankLine - 1, indexOfBlankLine)[0]
    val numberOfStacks = stackNumberLine.trim().split("   ").size

    val stackCharacterIndices = (1..numberOfStacks).map { stackNumber ->
        stackNumberLine.indexOfFirst { it.toString() == stackNumber.toString() }
    }

    val stacks = buildMap {
        (1..numberOfStacks).forEach {
            put(it, Stack<Char>())
        }
    }

    stackCharacterIndices.forEachIndexed { stackIndex, stackCharacterIndex ->
        stacksData.forEach { stackLine ->
            val stackCharacter = stackLine.getOrNull(stackCharacterIndex)
            if (stackCharacter != null && stackCharacter != ' ') {
                stacks.getValue(stackIndex + 1).push(stackCharacter)
            }
        }
    }

    movesData.forEach { moveData ->
        val (_, numberOfCrates, fromStack, toStack) =
            Regex("move (\\d*) from (\\d*) to (\\d*)")
                .find(moveData)!!
                .groupValues
        repeat(numberOfCrates.toInt()) {
            val crateLabelFromFirstStack = stacks.getValue(fromStack.toInt()).pop()
            stacks.getValue(toStack.toInt()).push(crateLabelFromFirstStack)
        }
    }

    println(
        stacks.map { it.value.peek() }.joinToString("")
    )
}