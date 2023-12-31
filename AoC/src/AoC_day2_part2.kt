import java.io.File

fun main() {
    var totalPowerSum = 0

    File("day2.txt").useLines { lines ->
        lines.forEach { line ->
            val (_, gameContents) = line.split(": ")
            val maxCubesInGame = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)

            gameContents.split("; ").forEach { subset ->
                val currentSubset = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)

                subset.split(", ").forEach { color ->
                    val (num, col) = color.split(" ")
                    currentSubset[col] = currentSubset[col]!! + num.toInt()
                }

                for (col in maxCubesInGame.keys) {
                    maxCubesInGame[col] = maxOf(maxCubesInGame[col]!!, currentSubset[col]!!)
                }
            }
            totalPowerSum += maxCubesInGame["red"]!! * maxCubesInGame["green"]!! * maxCubesInGame["blue"]!!
        }
    }

    println(totalPowerSum)
}
