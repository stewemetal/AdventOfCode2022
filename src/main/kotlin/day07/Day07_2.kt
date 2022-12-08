package day07

import readFileForDay
import java.io.File

fun main() {
    val lines = readFileForDay(7)

    val cd = "$ cd "
    val dir = "dir "

    val fileSystem = File("drive")
    fileSystem.apply {
        deleteRecursively()
        fileSystem.mkdir()
    }

    var currentDir = fileSystem

    lines.drop(1).forEach { line ->
        when {
            line.startsWith(cd) -> {
                when (val dirName = line.drop(cd.length)) {
                    ".." -> {
                        currentDir = currentDir.parentFile
                    }

                    else -> {
                        currentDir = File("$currentDir/$dirName")
                        currentDir.mkdir()
                    }
                }
            }

            line.startsWith(dir) -> {
                val dirName = line.drop(dir.length)
                File("$currentDir/$dirName").mkdir()
            }

            line.matches("\\d+\\s.*".toRegex()) -> {
                val (size, fileName) = line.split(" ")
                File("$currentDir/$fileName").apply {
                    createNewFile()
                    writeText(size)
                }
            }
        }
    }

    fun File.totalSize() =
        walkTopDown()
            .filter(File::isFile)
            .map { it.readText().toLong() }
            .sum()

    val totalSpaceUsed = fileSystem.totalSize()
    val totalSpaceUnused = 70_000_000L - totalSpaceUsed

    val requiredSpace = 30_000_000L - totalSpaceUnused
    val result = fileSystem.walkTopDown()
        .filter(File::isDirectory)
        .mapNotNull {
            val totalSize = it.totalSize()
            if(totalSize >= requiredSpace) {
                totalSize
            } else {
                null
            }
        }
        .min()

    println(result)

    fileSystem.deleteRecursively()
}
