import java.io.File
import kotlin.math.abs

fun main() {
    val file = File("day18.txt")
    val instructions = file.readLines().map { parseInstruction(it) }
    val points = extractPoints(instructions)
    val lagoonArea = shoelaceFormula(points)

    val perimeter = points.windowed(2, 1).sumOf {
        abs(it[1].first - it[0].first) + abs(it[1].second - it[0].second)
    }
    val adjustedArea = (lagoonArea + perimeter / 2 + 1).toInt()
    println(adjustedArea)
}

fun parseInstruction(line: String): Pair<Char, Int> {
    val parts = line.split(" ")
    return parts[0][0] to parts[1].toInt()
}

fun extractPoints(instructions: List<Pair<Char, Int>>): List<Pair<Int, Int>> {
    val points = mutableListOf(Pair(0, 0))
    var (x, y) = 0 to 0
    val directions = mapOf('R' to (1 to 0), 'D' to (0 to 1), 'L' to (-1 to 0), 'U' to (0 to -1))

    instructions.forEach { (dir, dist) ->
        val (dx, dy) = directions[dir]!!
        x += dx * dist
        y += dy * dist
        points.add(x to y)
    }
    return points
}

fun shoelaceFormula(points: List<Pair<Int, Int>>): Double {
    var area = 0.0
    for (i in points.indices) {
        val (x1, y1) = points[i]
        val (x2, y2) = points[(i + 1) % points.size]
        area += x1 * y2 - y1 * x2
    }
    return abs(area) / 2
}
