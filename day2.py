import operator

def add(intcode, pos):
    return __apply(intcode, pos, operator.add)

def multiply(intcode, pos):
    return __apply(intcode, pos, operator.mul)

def end(intcode, pos):
    pass

def run(intcode):
    def _run(intcode, pos):
        opcode = intcode[pos]
        if opcode == 99:
            return intcode
        else:
            intcode, pos = opcodes[opcode](intcode, pos)
            return _run(intcode, pos)

    return _run(intcode, 0)

opcodes = {
    1: add,
    2: multiply,
    99: end
}

def __apply(intcode, pos, func):
    result = func(intcode[intcode[pos+1]], intcode[intcode[pos+2]])
    dest = intcode[pos+3]
    intcode = list(intcode)
    intcode[dest] = result
    return tuple(intcode), pos+4

def run1202(intcode):
    intcode = list(intcode)
    intcode[1], intcode[2] = 12, 2
    return run(intcode)

memory = (1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,10,1,19,1,19,9,23,1,23,6,27,2,27,13,31,1,10,31,35,1,10,35,39,2,39,6,43,1,43,5,47,2,10,47,51,1,5,51,55,1,55,13,59,1,59,9,63,2,9,63,67,1,6,67,71,1,71,13,75,1,75,10,79,1,5,79,83,1,10,83,87,1,5,87,91,1,91,9,95,2,13,95,99,1,5,99,103,2,103,9,107,1,5,107,111,2,111,9,115,1,115,6,119,2,13,119,123,1,123,5,127,1,127,9,131,1,131,10,135,1,13,135,139,2,9,139,143,1,5,143,147,1,13,147,151,1,151,2,155,1,10,155,0,99,2,14,0,0)

def solve_part_2():
    for noun in range(100):
        for verb in range(100):
            intcode = list(memory)
            intcode[1], intcode[2] = noun, verb
            result = run(intcode)[0]
            if result == 19690720:
                print(100 * noun + verb)
                return

if __name__ == "__main__":
    print(run1202(memory)[0])
    solve_part_2()
