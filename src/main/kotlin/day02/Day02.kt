import OUTCOME.*

fun main() {

    val mySigns = mapOf(
        "R" to 1,
        "P" to 2,
        "S" to 3,
    )

    fun getResult(opponentSign: String, mySign: String): Int =
        when (opponentSign) {
            "A" -> {
                when (mySign) {
                    "X" -> mySigns["S"]!! + LOSS.value
                    "Y" -> mySigns["R"]!! + DRAW.value
                    "Z" -> mySigns["P"]!! + WIN.value
                    else -> error("Nope")
                }
            }
            "B" -> {
                when (mySign) {
                    "X" -> mySigns["R"]!! + LOSS.value
                    "Y" -> mySigns["P"]!! + DRAW.value
                    "Z" -> mySigns["S"]!! + WIN.value
                    else -> error("Nope")
                }
            }
            "C" -> {
                when (mySign) {
                    "X" -> mySigns["P"]!! + LOSS.value
                    "Y" -> mySigns["S"]!! + DRAW.value
                    "Z" -> mySigns["R"]!! + WIN.value
                    else -> error("Nope")
                }
            }
            else -> error("Nope")
        }

    var score = 0
        object {}.javaClass.getResourceAsStream("day02_input.txt")
            .bufferedReader()
            .forEachLine {
                val signs = it.split(" ")
                score += getResult(signs[0], signs[1])
            }
    println(
        score
    )
}

enum class OUTCOME(val value: Int) {
    LOSS(0),
    DRAW(3),
    WIN(6)
}