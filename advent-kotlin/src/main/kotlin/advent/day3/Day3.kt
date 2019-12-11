package advent.day3

import kotlin.math.abs
import kotlin.math.min

data class Point(val x: Int, val y: Int)

val moves = mapOf(
    'U' to { from: Point -> from.copy(y = from.y + 1) },
    'D' to { from: Point -> from.copy(y = from.y - 1) },
    'L' to { from: Point -> from.copy(x = from.x - 1) },
    'R' to { from: Point -> from.copy(x = from.x + 1) }
)


data class Wire(
    val current: Point = Point(0, 0),
    val points: Set<Point> = emptySet(),
    val steps: Map<Point, Int> = emptyMap(),
    val distance: Int = 0
)

fun travel(wire: Wire, path: String): Wire {
    val legs = path.split(',')

    val points = wire.points.toMutableSet() // adding to immutable sets is inefficient
    val steps = wire.steps.toMutableMap()
    var current = wire.current
    var distance = wire.distance

    for (leg in legs) {
        val move = moves.getValue(leg[0])
        val count = leg.substring(1).toInt()

        repeat(count) {
            current = move(current)
            points += current
            distance++
            steps.putIfAbsent(current, distance)
        }
    }
    return Wire(current, points, steps, distance)
}

fun wire(path: String) = travel(Wire(), path)

fun crosses(wire1: Wire, wire2: Wire) = wire1.points.intersect(wire2.points)

fun nearestIntersection(wire1: Wire, wire2: Wire) = nearestIntersection(crosses(wire1, wire2))

fun nearestIntersection(points: Set<Point>): Int {
    return points.fold(Int.MAX_VALUE) { acc, p -> min(acc, abs(p.x) + abs(p.y)) }
}

fun distance(wire: Wire, point: Point) = wire.steps.getOrElse(point) {
    throw IllegalArgumentException("Not found: $point")
}

fun shortestIntersection(wire1: Wire, wire2: Wire): Int {
    return crosses(wire1, wire2)
        .map { distance(wire1, it) + distance(wire2, it) }
        .fold(Int.MAX_VALUE) { acc, s -> min(acc, s) }
}
