import java.io.File

fun main() {
    val lines = File("day4.txt").readLines()
    var totalScratchcards = 0
    val copies = IntArray(lines.size) { 1 }

    for (i in lines.indices) {
        val matches = countMatches(lines[i])
        totalScratchcards += copies[i]
        for (j in 1..matches) {
            if (i + j < lines.size) {
                copies[i + j] += copies[i]
            }
        }
    }

    println(totalScratchcards)
}

fun countMatches(line: String): Int {
    val parts = line.substringAfter(": ").split(" | ")
    val winningNumbers = parts[0].split(" ")
        .filter { it.isNotBlank() }
        .map { it.toInt() }
    val yourNumbers = parts[1].split(" ")
        .filter { it.isNotBlank() }
        .map { it.toInt() }

    return yourNumbers.count { it in winningNumbers }
}



