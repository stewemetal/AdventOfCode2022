fun readFile(filePath: String): List<String> =
    object {}.javaClass.getResourceAsStream(filePath)
    .bufferedReader()
    .readLines()