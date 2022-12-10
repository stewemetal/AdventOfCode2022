package day08

import readFile
import readFileForDay


fun main() {
    fun isTreeVisibleInRow(forest: Array<IntArray>, rowIndex: Int, colIndex: Int): Boolean {
        val row = forest[rowIndex]
        val currentTree = row[colIndex]

        var visibleFromWest = true
        var visibleFromEast = true

        for (col in 0 until colIndex) {
            if (row[col] >= currentTree) {
                visibleFromWest = false
                break
            }
        }

        if (!visibleFromWest) {
            for (col in colIndex + 1..row.lastIndex) {
                if (row[col] >= currentTree) {
                    visibleFromEast = false
                    break
                }
            }
        }

        return visibleFromWest || visibleFromEast
    }

    check(
        "55616".run {
            isTreeVisibleInRow(
                Array(1) {
                    IntArray(length) { get(it).toString().toInt() }
                },
                0,
                4,
            )
        },
    )

    check(
        "55616".run {
            !isTreeVisibleInRow(
                Array(1) {
                    IntArray(length) { get(it).toString().toInt() }
                },
                0,
                1,
            )
        },
    )

    check(
        "65332".run {
            isTreeVisibleInRow(
                Array(1) {
                    IntArray(length) { get(it).toString().toInt() }
                },
                0,
                1,
            )
        },
    )

    fun isTreeVisibleInColumn(forest: Array<IntArray>, rowIndex: Int, colIndex: Int): Boolean {
        val currentTree = forest[rowIndex][colIndex]

        var visibleFromNorth = true
        var visibleFromSouth = true

        for (row in 0 until rowIndex) {
            if (forest[row][colIndex] >= currentTree) {
                visibleFromNorth = false
                break
            }
        }

        if (!visibleFromNorth) {
            for (row in rowIndex + 1..forest.lastIndex) {
                if (forest[row][colIndex] >= currentTree) {
                    visibleFromSouth = false
                    break
                }
            }
        }

        return visibleFromNorth || visibleFromSouth
    }

    fun countVisibleTrees(lines: List<String>): Int {
        val visibleTrees = mutableSetOf<Pair<Int, Int>>()

        val forest = Array(lines.size) { rowIndex ->
            IntArray(lines[rowIndex].length) { columnIndex ->
                lines[rowIndex][columnIndex].toString().toInt()
            }
        }

        forest.forEachIndexed { rowIndex, row ->
            row.indices.forEach inner@{ colIndex ->
                if (
                    rowIndex == 0
                    || rowIndex == forest.lastIndex
                    || colIndex == 0
                    || colIndex == row.lastIndex
                ) {
                    visibleTrees.add(Pair(rowIndex, colIndex))
                    return@inner
                }

                if (
                    isTreeVisibleInRow(forest, rowIndex, colIndex) ||
                    isTreeVisibleInColumn(forest, rowIndex, colIndex)
                ) {
                    visibleTrees.add(Pair(rowIndex, colIndex))
                }
            }
        }
        return visibleTrees.size
    }

    check(
        countVisibleTrees(
            readFile("day08_test.txt"),
        ) == 21,
    )

    println(
        countVisibleTrees(
            readFileForDay(8),
        ),
    )
}
