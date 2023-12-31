import java.io.File
import java.util.regex.Pattern

val spelledOutDigits = mapOf(
    "zero" to "0",
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9"
)

fun extractCalibrationValue(line: String): Int {
    val re = Pattern.compile("[0-9]|one|two|three|four|five|six|seven|eight|nine")
    var result = ""
    var currentDigit = ""

    for (char in line) {
        currentDigit += char.toString()
        val matcher = re.matcher(currentDigit)
        if (matcher.find()) {
            result = spelledOutDigits[matcher.group()] ?: matcher.group()
            currentDigit = ""
            break
        }
    }

    for (i in line.length - 1 downTo 0) {
        currentDigit = line[i] + currentDigit
        val matcher = re.matcher(currentDigit)
        if (matcher.find()) {
            result += spelledOutDigits[matcher.group()] ?: matcher.group()
            break
        }
    }

    return result.toIntOrNull() ?: 0
}

fun main() {
    val file = File("day1.txt")
    var total = 0

    file.forEachLine { line ->
        val calibrationValue = extractCalibrationValue(line)
        total += calibrationValue
    }

    println(total)
}
