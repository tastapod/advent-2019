
function parseOrbits(orbitData) {
    let orbits = {
        spaceObjects: new Map(), // centre -> list of satellites
        graph: new Map() // satellite -> centre
    }

    orbitData.split('\n').forEach((orbit) => {
        const [centre, satellite] = orbit.trim().split(')')
        orbits.graph.set(satellite, centre)
        const satellites = orbits.spaceObjects.get(centre) || []
        satellites.push(satellite)
        orbits.spaceObjects.set(centre, satellites)
    })

    return orbits
}

function traverseOrbits(spaceObjects) {
    const countLevel = (acc, level, node) => {
        const children = spaceObjects.get(node) || []
        return children.reduce(
            (acc, child) => countLevel(acc, level + 1, child),
            acc + level)
    }
    return countLevel(0, 0, 'COM')
}

exports.countOrbits = (orbitData) => {
    const spaceObjects = parseOrbits(orbitData).spaceObjects
    return traverseOrbits(spaceObjects);
}

exports.distanceBetween = (src, dst, orbitData) => {
    const graph = parseOrbits(orbitData).graph

    function append(acc, node) {
        acc.push(node)
        return node === "COM" ? acc : append(acc, graph.get(node));
    }

    function buildPath(node) {
        return append([], graph.get(node))
    }

    const srcPath = buildPath(src)
    const dstPath = buildPath(dst)
    return srcPath.reduce((result, node, i) => {
        if (result !== -1) return result
        const dstPos = dstPath.indexOf(node)
        return dstPos !== -1 ? i + dstPos : -1
    }, -1)
}