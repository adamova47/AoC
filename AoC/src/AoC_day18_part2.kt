import java.io.File
import kotlin.math.abs

fun main() {
    val file = File("day18.txt")
    val hexInstructions = parseHexInstructions(file)
    val points = extractPoints(hexInstructions)
    val lagoonArea = shoelaceFormula(points)

    val perimeter = points.windowed(2, 1).sumOf {
        abs(it[1].first - it[0].first).toLong() + abs(it[1].second - it[0].second)
    }
    val adjustedArea = (lagoonArea + perimeter / 2 + 1).toLong()
    println(adjustedArea)
}

fun parseHexInstructions(file: File): List<Pair<Char, Long>> {
    val instructions = mutableListOf<Pair<Char, Long>>()
    file.forEachLine { line ->
        val hexCode = line.split(" ")[2].substring(1)
        val instruction = convertHexToInstruction(hexCode)
        instructions.add(instruction)
    }
    return instructions
}

fun convertHexToInstruction(hexCode: String): Pair<Char, Long> {
    val distance = hexCode.substring(1, 6).toLong(16)
    val directionCode = hexCode[6]
    val directions = mapOf('0' to 'R', '1' to 'D', '2' to 'L', '3' to 'U')
    val direction = directions[directionCode]!!
    return direction to distance
}

fun extractPoints(instructions: List<Pair<Char, Long>>): List<Pair<Long, Long>> {
    val points = mutableListOf(Pair(0L, 0L))
    var (x, y) = 0L to 0L
    val directions = mapOf('R' to (1L to 0L), 'D' to (0L to 1L), 'L' to (-1L to 0L), 'U' to (0L to -1L))

    instructions.forEach { (dir, dist) ->
        val (dx, dy) = directions[dir]!!
        x += dx * dist
        y += dy * dist
        points.add(x to y)
    }
    return points
}

fun shoelaceFormula(points: List<Pair<Long, Long>>): Double {
    var area = 0.0
    for (i in points.indices) {
        val (x1, y1) = points[i]
        val (x2, y2) = points[(i + 1) % points.size]
        area += x1.toDouble() * y2 - y1.toDouble() * x2
    }
    return abs(area) / 2
}