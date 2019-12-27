exports.countOrbits = (orbits) => {
    let spaceObjects = new Map() // centre -> list of orbits

    // parse all the orbits
    orbits.split('\n').forEach((orbit) => {
        const [centre, obj] = orbit.trim().split(')')
        const objs = spaceObjects.get(centre) || []
        objs.push(obj)
        spaceObjects.set(centre, objs)
    })

    // traverse the tree
    const countLevel = (acc, level, node) => {
        const children = spaceObjects.get(node) || []
        return children.reduce(
            (acc, child) => countLevel(acc, level + 1, child),
            acc + level)
    }
    return countLevel(0, 0, 'COM')
}
