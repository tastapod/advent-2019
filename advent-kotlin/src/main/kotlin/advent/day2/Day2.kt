package advent.day2

open class Machine(private val intcode: List<Int>, private val pos: Int = 0) {
    private fun apply(op: (Int, Int) -> Int): Machine {
        val result = op(intcode[intcode[pos + 1]], intcode[intcode[pos + 2]])
        val dest = intcode[pos + 3]
        return Machine(
            pos = pos + 4,
            intcode = intcode.mapIndexed { i, x -> if (i == dest) result else x }
        )
    }

    fun add() = apply(Int::plus)

    fun multiply() = apply(Int::times)

    /** Override run() to terminate recursion */
    fun end(): Machine =
        object : Machine(intcode, pos) {
            override fun run() = intcode
        }


    /** Recursively invoke program until we get to an end instruction */
    open fun run(): List<Int> =
        invoke(intcode[pos]).run()

    fun invoke(opcode: Int) =
        when (opcode) {
            1 -> add()
            2 -> multiply()
            99 -> end()
            else -> {
                throw IllegalStateException("opcode = $opcode")
            }
        }

    // ------------------- generated code below here -------------------

    override fun toString(): String {
        return "Machine(intcode=$intcode, pos=$pos)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Machine) return false

        if (intcode != other.intcode) return false
        if (pos != other.pos) return false

        return true
    }

    override fun hashCode(): Int {
        var result = intcode.hashCode()
        result = 31 * result + pos
        return result
    }
}

