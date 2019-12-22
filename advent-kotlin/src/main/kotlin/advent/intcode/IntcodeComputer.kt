package advent.intcode

enum class Operator(
        val opcode: Int,
        val numParams: Int,
        val hasTarget: Boolean,
        val run: (IntcodeComputer) -> IntcodeComputer) {

    ADD(1, 2, true, IntcodeComputer::add),
    MULTIPLY(2, 2, true, IntcodeComputer::multiply),
    READ(3, 0, true, IntcodeComputer::read),
    WRITE(4, 1, false, IntcodeComputer::write),
    STOP(99, 0, false, IntcodeComputer::stop);

    companion object {
        fun forOpcode(opcode: Int) = values().first { it.opcode == opcode }
    }
}

data class IntcodeComputer(
        val program: List<Int>,
        val pos: Int = 0,
        val input: List<Int> = emptyList(),
        val output: List<Int> = emptyList()
) {
    private val instruction by lazy { Instruction() }

    inner class Instruction() {
        val opcode: Int = program[pos] % 100
        private val op = Operator.forOpcode(opcode)
        private val values: List<Int> = (1..op.numParams).map {
            program[program[pos + it]] // TODO handle immediate params
        }
        val targetIndex = if (op.hasTarget) program[pos + op.numParams + 1] else null

        operator fun get(i: Int) = values[i]

        fun run() = op
                .run(this@IntcodeComputer)
    }

    fun run() = instruction.run()

    fun runToEnd(): IntcodeComputer {
        tailrec fun recur(computer: IntcodeComputer): IntcodeComputer =
                if (computer.instruction.opcode == Operator.STOP.opcode) computer
                else recur(computer.run())
        return recur(this)
    }

    private fun invokeBinary(op: (Int, Int) -> Int): IntcodeComputer {
        return put(instruction.targetIndex!!, op(instruction[0], instruction[1]))
                .advance(4)
    }

    fun add(): IntcodeComputer {
        return invokeBinary(Int::plus)
    }

    fun multiply(): IntcodeComputer {
        return invokeBinary(Int::times)
    }

    fun read(): IntcodeComputer {
        return put(instruction.targetIndex!!, input.first())
                .copy(input = input.drop(1))
                .advance(2)
    }

    fun write(): IntcodeComputer {
        return copy(output = output + instruction[0]).advance(2)
    }

    fun stop(): IntcodeComputer {
        return this
    }

    // utility functions

    fun withNounAndVerb(noun: Int, verb: Int) = put(1, noun).put(2, verb)

    private fun put(ix: Int, value: Int) =
            copy(program = program.toMutableList().also { it[ix] = value }.toList())

    private fun advance(steps: Int) = copy(pos = pos + steps)
}
