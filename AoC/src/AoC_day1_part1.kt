import java.io.File

fun calibrationValue(line: String): Int {
    val digits = line.filter { it.isDigit() }
    return if (digits.isEmpty()) 0 else "${digits.first()}${digits.last()}".toInt()
}

fun sumCalibrationValues(lines: List<String>): Int {
    return lines.sumOf { calibrationValue(it) }
}

fun main() {
    val linesOfText = File("day1.txt").readLines()
    val result = sumCalibrationValues(linesOfText)
    println(result)
}
