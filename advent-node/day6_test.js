const assert = require('assert')
const {describe, it} = require('mocha')

const fs = require('fs')
const {countOrbits, distanceBetween: distanceBetween} = require('./day6')

describe('map reader', () => {
    it('counts a single node', () => {
        assert.equal(countOrbits('COM)A'), 1)
    })

    it('counts several nodes', () => {
        assert.equal(countOrbits(
            `COM)A
            A)B`), 3)
    })

    it('counts example nodes', () => {
        assert.equal(countOrbits(
            `COM)B
             B)C
             C)D
             D)E
             E)F
             B)G
             G)H
             D)I
             E)J
             J)K
             K)L`),
            42)
    })

    it('ignores order of input orbits', () => {
        assert.equal(countOrbits(
            `COM)B
             E)J
             J)K
             B)C
             B)G
             G)H
             C)D
             D)E
             E)F
             D)I
             K)L`),
            42)
    })

    it("calculates distance between two nodes", () => {
        assert.equal(distanceBetween("A", "B",
            `COM)A2
             A2)A1
             A1)A
             COM)B3
             B3)B2
             B2)B1
             B1)B`),
            2 + 3)
    })
})

describe("solving day 6", () => {
    function readOrbitData() {
        return fs.readFileSync("day6.dat", "utf8");
    }

    it("counts orbits for part 1", () => {
        console.log(countOrbits(readOrbitData()))
        // 234446
    })

    it("counts distance for part 2", () => {
        console.log(distanceBetween("YOU", "SAN", readOrbitData()))
        // 385
    })
})