package advent.intcode

data class IntcodeComputer(
        val program: List<Int>,
        val pos: Int = 0,
        val input: List<Int> = emptyList(),
        val output: List<Int> = emptyList()
) {
    private val opcode: Int = program[pos] // not involved in data class properties

    inner class Instruction(numParams: Int, hasTarget: Boolean) {
        private val values: List<Int> = (1..numParams).map {
            program[program[pos + it]] // TODO handle immediate params
        }
        val targetIndex = if (hasTarget) program[pos + numParams + 1] else null

        operator fun get(i: Int) = values[i]
    }

    fun run() = when (opcode % 100) {
        1 -> add()
        2 -> multiply()
        3 -> read()
        4 -> write()
        99 -> stop()
        else -> throw IllegalArgumentException("Unexpected opcode: $opcode")
    }

    fun runToEnd(): IntcodeComputer {
        tailrec fun recur(computer: IntcodeComputer): IntcodeComputer =
                if (computer.opcode == 99) computer else recur(computer.run())
        return recur(this)
    }

    private fun invokeBinary(op: (Int, Int) -> Int): IntcodeComputer {
        val instruction = Instruction(2, true)
        return put(instruction.targetIndex!!, op(instruction[0], instruction[1]))
                .advance(4)
    }

    private fun add(): IntcodeComputer {
        return invokeBinary(Int::plus)
    }

    private fun multiply(): IntcodeComputer {
        return invokeBinary(Int::times)
    }

    private fun read(): IntcodeComputer {
        val instruction = Instruction(0, true)
        return put(instruction.targetIndex!!, input.first())
                .copy(input = input.drop(1))
                .advance(2)
    }

    private fun write(): IntcodeComputer {
        val instruction = Instruction(1, false)
        return copy(output = output + instruction[0]).advance(2)
    }

    private fun stop(): IntcodeComputer {
        return this
    }

    // utility functions

    fun withNounAndVerb(noun: Int, verb: Int) = put(1, noun).put(2, verb)

    private fun put(ix: Int, value: Int) =
            copy(program = program.toMutableList().also { it[ix] = value }.toList())

    private fun advance(steps: Int) = copy(pos = pos + steps)
}
