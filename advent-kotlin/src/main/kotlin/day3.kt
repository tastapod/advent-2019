package com.tastapod.advent.day3

import kotlin.math.min
import kotlin.math.abs

data class Point(val x: Int, val y: Int)

data class Wire(var current: Point = Point(0, 0), val points: MutableSet<Point> = mutableSetOf()) {
    private val moves = mapOf(
        'U' to { from: Point -> from.copy(y = from.y + 1) },
        'D' to { from: Point -> from.copy(y = from.y - 1) },
        'L' to { from: Point -> from.copy(x = from.x - 1) },
        'R' to { from: Point -> from.copy(x = from.x + 1) }
    )

    fun travel(path: String) {
        val legs = path.split(',')

        for (leg in legs) {
            val move = moves.getValue(leg[0])
            val count = leg.substring(1).toInt()

            repeat(count) {
                current = move(current).also { points.add(it) }
            }
        }
    }

    fun crosses(other: Wire): Set<Point> = points.intersect(other.points)
}


fun nearest(points: Set<Point>): Int {
    return points.fold(Int.MAX_VALUE) { acc, p -> min(acc, abs(p.x) + abs(p.y)) }
}
