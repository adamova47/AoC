import java.io.File

fun sumOfPartNumbers(schematic: List<String>): Int {
    var sum = 0

    schematic.forEachIndexed { i, row ->
        Regex("\\d+").findAll(row).forEach { matchResult ->
            val number = matchResult.value.toInt()
            val numberStartIndex = matchResult.range.first
            val numberEndIndex = matchResult.range.last

            if (isNumberAdjacentToSymbol(schematic, i, numberStartIndex, numberEndIndex)) {
                sum += number
            }
        }
    }

    return sum
}

fun isNumberAdjacentToSymbol(schematic: List<String>, row: Int, start: Int, end: Int): Boolean {
    for (i in row - 1..row + 1) {
        for (j in start - 1..end + 1) {
            if (i in schematic.indices && j in schematic[0].indices) {
                val char = schematic[i][j]
                if (!char.isDigit() && char != '.') {
                    return true
                }
            }
        }
    }
    return false
}

fun main() {
    val schematic = File("day3.txt").readLines()
    println(sumOfPartNumbers(schematic))
}
