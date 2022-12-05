import OUTCOME.*

fun main() {

    val mySigns = mapOf(
        "X" to 1,
        "Y" to 2,
        "Z" to 3,
    )

    fun getResult(opponentSign: String, mySign: String): Int =
        when (opponentSign) {
            "A" -> {
                when (mySign) {
                    "X" -> mySigns[mySign]!! + DRAW.value
                    "Y" -> mySigns[mySign]!! + WIN.value
                    "Z" -> mySigns[mySign]!! + LOSS.value
                    else -> error("Nope")
                }
            }
            "B" -> {
                when (mySign) {
                    "X" -> mySigns[mySign]!! + LOSS.value
                    "Y" -> mySigns[mySign]!! + DRAW.value
                    "Z" -> mySigns[mySign]!! + WIN.value
                    else -> error("Nope")
                }
            }
            "C" -> {
                when (mySign) {
                    "X" -> mySigns[mySign]!! + WIN.value
                    "Y" -> mySigns[mySign]!! + LOSS.value
                    "Z" -> mySigns[mySign]!! + DRAW.value
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