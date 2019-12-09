import day1

def test_calculate_fuel():
    assert day1.calculate_fuel(12) == 2
    assert day1.calculate_fuel(14) == 2
    assert day1.calculate_fuel(1969) == 654
    assert day1.calculate_fuel(100756) == 33583

def test_calculate_extra():
    assert day1.calculate_extra_fuel(14) == 2
    assert day1.calculate_extra_fuel(1969) == 966
    assert day1.calculate_extra_fuel(100756) == 50346
