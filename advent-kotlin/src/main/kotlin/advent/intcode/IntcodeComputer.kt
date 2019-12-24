package advent.intcode

data class IntcodeComputer(
        val program: List<Int>,
        val pos: Int = 0,
        val input: List<Int> = emptyList(),
        val output: List<Int> = emptyList()
) {
    private val opcode: Int // separate from data class properties
        get() = program[pos]

    inner class Instruction(numParams: Int, hasTarget: Boolean) {
        private val values: List<Int> = run {
            var argTypes = opcode / 100
            (1..numParams).map {
                val isImmediate = argTypes % 10 == 1
                argTypes /= 10
                if (isImmediate) program[pos + it] else program[program[pos + it]]
            }
        }
        val targetIndex = if (hasTarget) program[pos + numParams + 1] else null

        operator fun get(i: Int) = values[i]
    }

    fun run(): IntcodeComputer =
            when (opcode % 100) {
                1 -> add()
                2 -> multiply()
                3 -> read()
                4 -> write()
                5 -> jumpIfTrue()
                6 -> jumpIfFalse()
                7 -> storeIfLessThan()
                8 -> storeIfEqual()
                99 -> stop()
                else -> throw IllegalArgumentException("Unexpected opcode: $opcode")
            }

    fun runToEnd(): IntcodeComputer {
        tailrec fun recurse(computer: IntcodeComputer): IntcodeComputer =
                if (computer.opcode == 99) computer else recurse(computer.run())
        return recurse(this)
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

    private fun read(): IntcodeComputer =
            Instruction(0, true).let {
                put(it.targetIndex!!, input.first())
                        .copy(input = input.drop(1))
                        .advance(2)
            }

    private fun write(): IntcodeComputer =
            Instruction(1, false).let {
                copy(output = output + it[0]).advance(2)
            }

    private fun stop(): IntcodeComputer = this

    private fun jumpIf(cond: (Int) -> Boolean): IntcodeComputer =
            Instruction(2, false).let {
                if (cond(it[0])) copy(pos = it[1]) else advance(4)
            }

    private fun jumpIfTrue() = jumpIf { it == 0 }
    private fun jumpIfFalse() = jumpIf { it != 0 }

    private fun storeIf(cond: (Int, Int) -> Boolean): IntcodeComputer =
            Instruction(2, true).let {
                put(it.targetIndex!!, if (cond(it[0], it[1])) 1 else 0).advance(4)
            }

    private fun storeIfLessThan() = storeIf { a, b -> a < b }
    private fun storeIfEqual() = storeIf { a, b -> a == b }

    // utility functions

    fun withNounAndVerb(noun: Int, verb: Int) = put(1, noun).put(2, verb)

    private fun put(ix: Int, value: Int) =
            copy(program = program.toMutableList().also { it[ix] = value }.toList())

    private fun advance(steps: Int) = copy(pos = pos + steps)
}
