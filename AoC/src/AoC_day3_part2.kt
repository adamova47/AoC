import java.io.File

fun main() {
    val schematic = File("day3.txt").readLines()
    println(sumOfGearRatios(schematic))
}

fun sumOfGearRatios(schematic: List<String>): Long {
    var totalGearRatio = 0L

    for (row in schematic.indices) {
        for (col in schematic[row].indices) {
            if (schematic[row][col] == '*') {
                val numbers = getAdjacentNumbers(schematic, row, col)
                if (numbers.size == 2) {
                    totalGearRatio += numbers[0].toLong() * numbers[1].toLong()
                }
            }
        }
    }

    return totalGearRatio
}

fun getAdjacentNumbers(schematic: List<String>, row: Int, col: Int): List<Int> {
    val numbers = mutableListOf<Int>()
    val numberPos = mutableSetOf<Pair<Int, Int>>()
    val neighbors = arrayOf(intArrayOf(-1, -1), intArrayOf(-1, 0), intArrayOf(-1, 1), intArrayOf(1, -1), intArrayOf(1, 0), intArrayOf(1, 1), intArrayOf(0, -1), intArrayOf(0, 1))

    for (neigh in neighbors) {
        val y = row + neigh[0]
        val x = col + neigh[1]

        if (y in schematic.indices && x in schematic[y].indices && schematic[y][x] in '0'..'9') {
            var newX = x
            while (newX >= 0 && schematic[y][newX] in '0'..'9') {
                newX--
            }
            newX++
            val pos = Pair(y, newX)
            if (!numberPos.contains(pos)) {
                numberPos.add(pos)
                val start = newX
                while (newX < schematic[y].length && schematic[y][newX] in '0'..'9') {
                    newX++
                }
                newX--
                numbers.add(schematic[y].substring(start, newX + 1).toInt())
            }
        }
    }

    return numbers
}



