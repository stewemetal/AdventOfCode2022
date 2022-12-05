fun main() {
    println(
        object {}.javaClass.getResourceAsStream("day01_input.txt")
            .bufferedReader()
            .readLines()
            .run {
                var maxValue = 0L
                var remainingList = this
                while (remainingList.isNotEmpty()) {
                    val currentFoodValue = remainingList
                        .takeWhile { it.isNotBlank() }
                        .sumOf { it.toLong() }
                    if (currentFoodValue > maxValue) {
                        maxValue = currentFoodValue
                    }
                    remainingList = remainingList.dropWhile { it.isNotBlank() }.drop(1)
                }
                maxValue
            }
    )
}