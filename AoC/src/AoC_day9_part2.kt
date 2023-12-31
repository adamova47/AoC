import java.io.File

fun main() {
    val histories = readHistories("day9.txt")
    val sumBackward = histories.sumOf { extrapolateNextValue(it.asReversed()) }
    println(sumBackward)
}

/*
fun readHistories(filePath: String): List<List<Int>> {
    return File(filePath).readLines().map { line ->
        line.split(" ").map { it.toInt() }
    }
}

fun extrapolateNextValue(sequence: List<Int>): Int {
    val differences = sequence.windowed(2) { it[1] - it[0] }
    return if (differences.all { it == 0 }) {
        sequence.last()
    } else {
        sequence.last() + extrapolateNextValue(differences)
    }
}
 */

