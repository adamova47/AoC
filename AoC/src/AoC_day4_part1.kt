import java.io.File

fun main() {
    val totalPoints = File("day4.txt").readLines().sumOf { line ->
        val numbersPart = line.substringAfter(": ")
        val (winning, your) = numbersPart.split(" | ").map { it ->
            it.split(" ")
            .filter { it.isNotEmpty() }.map { it.toInt() } }
        calculateCardPoints(winning, your)
    }
    println(totalPoints)
}

fun calculateCardPoints(winning: List<Int>, your: List<Int>): Int {
    var points = 0
    your.forEach { number ->
        if (number in winning) {
            points = if (points == 0) 1 else points * 2
        }
    }
    return points
}
