import day2
import pytest

def test_add():
    assert day2.add(
        (None, None, None, None, 1, 10, 9, 8, None, 11, 22), 4) == (
        (None, None, None, None, 1, 10, 9, 8, 33, 11, 22), 8)

def test_multiply():
    assert day2.multiply(
        (None, None, None, None, 1, 10, 9, 8, None, 11, 22), 4) == (
        (None, None, None, None, 1, 10, 9, 8, 242, 11, 22), 8)

def test_run():
    assert day2.run((
            1,9,10,3,
            2,3,11,0,
            99,
            30,40,50)
        ) == (
            3500,9,10,70,
            2,3,11,0,
            99,
            30,40,50
        )

@pytest.mark.parametrize("input, expected", [
    [(1, 0, 0, 0, 99), (2, 0, 0, 0, 99)],
    [(2, 3, 0, 3, 99), (2, 3, 0, 6, 99)],
    [(2, 4, 4, 5, 99, 0), (2, 4, 4, 5, 99, 9801)],
])
def test_run_mini(input, expected):
    assert day2.run(input) == expected