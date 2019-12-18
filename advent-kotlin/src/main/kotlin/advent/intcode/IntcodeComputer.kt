package advent.intcode

data class IntcodeComputer(val intcode: List<Int>, val pos: Int = 0) {
    private val opcode = intcode[pos] % 100

    fun run(): List<Int> {
        var computer = this
        do {
            val operation = Operator[computer.opcode]
            computer = operation.invoke(computer)
        } while (operation != Operator.STOP)
        return computer.intcode
    }

    fun args(op: Operator): Args {
        val targetPos = if (op.hasTarget) pos + op.paramCount + 1 else null

        return Args(
            values = (1..op.paramCount).map { intcode[intcode[pos + it]] },
            target = if (targetPos != null) intcode[targetPos] else null
        )
    }

    fun withNounAndVerb(noun: Int, verb: Int) =
        IntcodeComputer(intcode.toMutableList().also {
            it[1] = noun
            it[2] = verb
        }.toList(), pos)
}

data class Args(val values: List<Int> = emptyList(), val target: Int? = null)

enum class Operator(val paramCount: Int, val hasTarget: Boolean) {
    STOP(paramCount = 0, hasTarget = false) {
        override operator fun invoke(computer: IntcodeComputer) = computer
    },

    ADD(paramCount = 2, hasTarget = true) {
        override operator fun invoke(computer: IntcodeComputer): IntcodeComputer {
            return invokeBinary(computer, Int::plus)
        }
    },

    MULTIPLY(paramCount = 2, hasTarget = true) {
        override operator fun invoke(computer: IntcodeComputer): IntcodeComputer {
            return invokeBinary(computer, Int::times)
        }
    };

    abstract operator fun invoke(computer: IntcodeComputer): IntcodeComputer

    protected fun invokeBinary(computer: IntcodeComputer, op: (Int, Int) -> Int): IntcodeComputer {
        val args = computer.args(this)
        val result = computer.intcode.toMutableList().also {
            it[args.target!!] = op(args.values[0], args.values[1])
        }

        return IntcodeComputer(
            intcode = result.toList(),
            pos = computer.pos + 1 + paramCount + if (hasTarget) 1 else 0
        )
    }

    companion object {
        operator fun get(i: Int) =
            when (i) {
                1 -> ADD
                2 -> MULTIPLY
                99 -> STOP
                else -> throw IllegalArgumentException("Unknown opcode $i")
            }
    }
}
