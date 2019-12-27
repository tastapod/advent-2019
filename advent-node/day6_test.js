const assert = require('assert')
const {describe, it} = require('mocha')

const fs = require('fs')
const {countOrbits} = require('./day6')

describe('Testing', () => {
    it('runs tests', () => {
        assert.equal(true, true)
    })
})

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

    it('counts nodes for part 1', () => {
        assert.equal(countOrbits(fs.readFileSync('day6.dat', 'utf8')), 234446)
    })
})
