import java.io.File

fun main() {
    val lines = File("day5.txt").readLines()

    val seeds = lines.first().removePrefix("seeds:").trim().split(" ").map { it.toLong() }
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

    val convertedNumbers = seeds.map { convertThroughMaps(it, maps) }
    val minLocationNumber = convertedNumbers.minOrNull()

    println(minLocationNumber)
}

fun convertThroughMaps(number: Long, maps: Map<String, List<Triple<Long, Long, Int>>>): Long {
    var convertedNumber = number
    for (map in maps.values) {
        var found = false
        for ((destinationStart, sourceStart, length) in map) {
            if (sourceStart <= convertedNumber && convertedNumber < sourceStart + length) {
                val offset = convertedNumber - sourceStart
                convertedNumber = destinationStart + offset
                found = true
                break
            }
        }
        if (!found) convertedNumber = convertedNumber
    }
    return convertedNumber
}
