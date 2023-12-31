import java.io.File

fun main() {
    val maxCubes = mapOf("red" to 12, "green" to 13, "blue" to 14)
    val possibleGames = mutableListOf<Int>()

    File("day2.txt").useLines { lines ->
        lines.forEach { line ->
            val (gameId, gameContents) = line.split(": ")
            val cubes = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)

            gameContents.split("; ").forEach { subset ->
                subset.split(", ").forEach { color ->
                    val (num, col) = color.split(" ")
                    cubes[col] = maxOf(cubes[col]!!, num.toInt())
                }
            }

            if (cubes.all { (color, count) -> count <= maxCubes[color]!! }) {
                possibleGames.add(gameId.removePrefix("Game ").toInt())
            }
        }
    }

    val sumOfPossibleGameIds = possibleGames.sum()
    println(sumOfPossibleGameIds)
}
