fun main() {
    println(
        object {}.javaClass.getResourceAsStream("day01_input.txt")
            .bufferedReader()
            .readLines()
            .run {
                val maxValues = mutableListOf<Long>()
                var remainingList = this
                while (remainingList.isNotEmpty()) {
                    maxValues.add(
                        remainingList
                            .takeWhile { it.isNotBlank() }
                            .sumOf { it.toLong() }
                    )
                    remainingList = remainingList.dropWhile { it.isNotBlank() }.drop(1)
                }
                maxValues.apply {
                    sortDescending()
                }
                    .take(3)
                    .sum()
            }
    )
}