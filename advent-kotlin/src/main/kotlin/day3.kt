package com.tastapod.advent.day3

import java.lang.IllegalArgumentException
import kotlin.math.min
import kotlin.math.abs

data class Point(val x: Int, val y: Int)

data class Wire(var current: Point = Point(0, 0), val points: MutableSet<Point> = mutableSetOf()) {
    constructor(path: String) : this() {
        travel(path)
    }

    private val moves = mapOf(
        'U' to { from: Point -> from.copy(y = from.y + 1) },
        'D' to { from: Point -> from.copy(y = from.y - 1) },
        'L' to { from: Point -> from.copy(x = from.x - 1) },
        'R' to { from: Point -> from.copy(x = from.x + 1) }
    )

    private var _distance = 0
    private val _steps = mutableMapOf<Point, Int>()

    fun travel(path: String) {
        val legs = path.split(',')

        for (leg in legs) {
            val move = moves.getValue(leg[0])
            val count = leg.substring(1).toInt()

            repeat(count) {
                current = move(current)
                points.add(current)
                _steps.putIfAbsent(current, ++_distance)
            }
        }
    }

    fun crosses(other: Wire): Set<Point> = points.intersect(other.points)

    fun steps(point: Point): Int {
        return _steps.getOrElse(point) {
            throw IllegalArgumentException("Not found: $point")
        }
    }
}

fun nearest(points: Set<Point>): Int {
    return points.fold(Int.MAX_VALUE) { acc, p -> min(acc, abs(p.x) + abs(p.y)) }
}

fun shortest(wire1: Wire, wire2: Wire): Int {
    return wire1.crosses(wire2).map { wire1.steps(it) + wire2.steps(it) }.fold(Int.MAX_VALUE) {acc, s -> min(acc, s)}
}