fun readFile(filePath: String): List<String> =
    object {}.javaClass.getResourceAsStream(filePath)
    .bufferedReader()
    .readLines()

fun readFileForDay(day: Int): List<String> =
    object {}.javaClass.getResourceAsStream(
        "day${day.asDayNumber()}_input.txt",
        )!!
        .bufferedReader()
        .readLines()

private fun Int.asDayNumber() =
    if(this.toString().length == 1) {
        "0$this"
    } else {
        this.toString()
    }