package day03

val Char.value
    get() =
        when (this) {
            in 'a'..'z' -> this.code - ('a'.code) + 1
            in 'A'..'Z' -> this.code - ('A'.code) + 27
            else -> 0
        }