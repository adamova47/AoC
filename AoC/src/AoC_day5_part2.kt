import java.io.File

fun main() {
    val lines = File("day5.txt").readLines()

    val seedRangePairs = lines.first().removePrefix("seeds:").trim().split(" ").windowed(2, 2)
    val maps = mutableMapOf<String, MutableList<Triple<Long, Long, Int>>>()
    var currentMapKey = ""

    for (line in lines.drop(1)) {
        if (line.endsWith("map:")) {
            currentMapKey = line.split(":")[0]
            maps[currentMapKey] = mutableListOf()
        } else if (line.isNotBlank()) {
            val parts = line.split(" ").map { it.toLong() }
            maps[currentMapKey]?.add(Triple(parts[0], parts[1], parts[2].toInt()))
        }
    }

    var minLocationNumber = Long.MAX_VALUE

    for (pair in seedRangePairs) {
        val start = pair[0].toLong()
        val length = pair[1].toInt()
        for (seed in start until (start + length)) {
            val locationNumber = convertThroughMaps(seed, maps)
            if (locationNumber < minLocationNumber) {
                minLocationNumber = locationNumber
            }
        }
    }

    println(minLocationNumber)
}