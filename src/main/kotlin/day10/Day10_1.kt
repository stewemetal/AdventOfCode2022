import java.security.InvalidParameterException

fun main() {

    val testInstructions = readFile("day10_test.txt")
    val testCpu = CPU()
    testInstructions.forEach { instruction ->
        testCpu.runInstruction(instruction)
    }
    check(testCpu.signalStrength == 13140)

    val instructions = readFileForDay(10)
    val cpu = CPU()
    instructions.forEach { instruction ->
        cpu.runInstruction(instruction)
    }

    println(cpu.signalStrength)
}

class CPU {
    private var registerX: Int = 1

    var signalStrength = 0
    private var cycleCounter: Int = 0
        set(value) {
            field = value
            when (value) {
                20,
                60,
                100,
                140,
                180,
                220 -> signalStrength += value * registerX
            }
        }

    private fun noop() {
        cycleCounter++
    }

    private fun addx(value: Int) {
        cycleCounter++
        cycleCounter++
        registerX += value
    }

    fun runInstruction(instruction: String) {
        when {
            instruction == "noop" -> noop()
            instruction.startsWith("addx") -> {
                addx(instruction.split(" ")[1].toInt())
            }

            else -> throw InvalidParameterException()
        }
    }
}