import java.security.InvalidParameterException

fun main() {

    val gpu = GPU()

    val testInstructions = readFile("day10_test.txt")
    val testCpu = CPU_2(gpu)
    testInstructions.forEach { instruction ->
        testCpu.runInstruction(instruction)
    }

    println()
    println()

    val instructions = readFileForDay(10)
    val cpu = CPU_2(gpu)
    instructions.forEach { instruction ->
        cpu.runInstruction(instruction)
    }
}

class GPU {
    fun draw(cycleCounter: Int, registerValue: Int) {
        if(
            cycleCounter > 1 && cycleCounter.mod(40) == 1
        ) {
            println()
        }

        if(cycleCounter.mod(40) in registerValue..registerValue+2) {
            print("#")
        } else {
            print(".")
        }
    }
}

class CPU_2(
    private val gpu: GPU,
) {
    private var registerX: Int = 1
    private var cycleCounter: Int = 0
        set(value) {
            field = value
            gpu.draw(field, registerX)
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