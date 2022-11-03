import math
from contextlib import closing

def calculate_fuel(mass):
    return math.floor(mass / 3) - 2

def calculate_extra_fuel(fuel):
    def calc(fuel, acc):
        extra = calculate_fuel(fuel)
        return acc if extra <= 0 else calc(extra, acc + extra)
    return calc(fuel, 0)

if __name__ == "__main__":
    total = 0

    with closing(open("day1.data")) as fp:
        masses = fp.readlines()

    for mass in masses:
        fuel = calculate_fuel(int(mass.rstrip()))
        extra = calculate_extra_fuel(fuel)
        total += fuel + extra

    print(total)
