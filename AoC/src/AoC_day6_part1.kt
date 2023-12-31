import java.io.File

fun main() {
    val lines = File("day6.txt").readLines()

    val times = lines[0].split(":")[1].trim().split("\\s+".toRegex()).map { it.toInt() }
    val distances = lines[1].split(":")[1].trim().split("\\s+".toRegex()).map { it.toInt() }

    val races = times.zip(distances)
    val result = races.map { (time, distance) ->
        calculateWays(time, distance)
    }.reduce { acc, ways -> acc * ways }

    println(result)
}

fun calculateWays(time: Int, distance: Int): Int {
    var ways = 0
    for (holdTime in 0 until time) {
        val speed = holdTime
        val travelTime = time - holdTime
        val totalDistance = speed * travelTime
        if (totalDistance > distance) {
            ways++
        }
    }
    return ways
}
