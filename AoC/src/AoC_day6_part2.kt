import java.io.File

fun main() {
    val lines = File("day6.txt").readLines()

    val time = lines[0].split(":")[1].replace(" ", "").toLong()
    val distance = lines[1].split(":")[1].replace(" ", "").toLong()
    val ways = calculateWays(time, distance)

    println(ways)
}

fun calculateWays(time: Long, distance: Long): Int {
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
