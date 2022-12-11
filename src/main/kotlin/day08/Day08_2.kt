package day08

import readFile
import readFileForDay
import kotlin.math.max


fun main() {

    fun scenicScoreWestEast(forest: Array<IntArray>, rowIndex: Int, colIndex: Int): Int {
        val row = forest[rowIndex]
        val currentTree = row[colIndex]

        var viewingDistanceWest = 0
        var viewingDistanceEast = 0

        for (col in colIndex - 1 downTo 0) {
            viewingDistanceWest++
            if (row[col] >= currentTree) {
                break
            }
        }

        for (col in colIndex + 1..row.lastIndex) {
            viewingDistanceEast++
            if (row[col] >= currentTree) {
                break
            }
        }

        return viewingDistanceWest * viewingDistanceEast
    }

    fun scenicScoreNorthSouth(forest: Array<IntArray>, rowIndex: Int, colIndex: Int): Int {
        val currentTree = forest[rowIndex][colIndex]

        var viewingDistanceNorth = 0
        var viewingDistanceSouth = 0

        for (row in rowIndex - 1 downTo 0) {
            viewingDistanceNorth++
            if (forest[row][colIndex] >= currentTree) {
                break
            }
        }

        for (row in rowIndex + 1..forest.lastIndex) {
            viewingDistanceSouth++
            if (forest[row][colIndex] >= currentTree) {
                break
            }
        }

        return viewingDistanceNorth * viewingDistanceSouth
    }

    fun getMaxScenicScore(lines: List<String>): Int {
        var maxScenicScore = 0

        val forest = Array(lines.size) { rowIndex ->
            IntArray(lines[rowIndex].length) { columnIndex ->
                lines[rowIndex][columnIndex].toString().toInt()
            }
        }

        forest.forEachIndexed { rowIndex, row ->
            if (rowIndex == 0 || rowIndex == row.lastIndex) return@forEachIndexed
            row.indices.forEach inner@{ colIndex ->
                if (colIndex == 0 || colIndex == row.lastIndex) {
                    return@inner
                }

                val scenicScore = scenicScoreWestEast(forest, rowIndex, colIndex) *
                        scenicScoreNorthSouth(forest, rowIndex, colIndex)

                maxScenicScore = max(scenicScore, maxScenicScore)
            }
        }
        return maxScenicScore
    }

    println(
        getMaxScenicScore(
            readFile("day08_test.txt"),
        )
    )

    check(
        getMaxScenicScore(
            readFile("day08_test.txt"),
        ) == 8,
    )

    println(
        getMaxScenicScore(
            readFileForDay(8),
        ),
    )
}
