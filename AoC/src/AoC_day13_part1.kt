import java.io.File

fun findHorizontalSymmetry(map: List<String>): Int {
    for (index in 0 until map.size - 1) {
        var mismatchFound = false
        var upperIndex = index
        var lowerIndex = index + 1
        while (upperIndex >= 0 && lowerIndex < map.size) {
            for (charIndex in map[0].indices) {
                if (map[upperIndex][charIndex] != map[lowerIndex][charIndex]) {
                    mismatchFound = true
                    break
                }
            }
            if (mismatchFound) break
            upperIndex--
            lowerIndex++
        }
        if (!mismatchFound) return index + 1
    }
    return 0
}

fun findVerticalSymmetry(map: List<String>): Int {
    val transposedMap = map[0].indices.map { col ->
        map.joinToString("") { it[col].toString() }
    }
    return findHorizontalSymmetry(transposedMap)
}

fun main() {
    val maps = mutableListOf<List<String>>()

    File("day13.txt").readLines().let { lines ->
        var currentMap = mutableListOf<String>()
        lines.forEach { line ->
            when {
                line.isNotEmpty() -> currentMap.add(line)
                else -> {
                    maps.add(currentMap)
                    currentMap = mutableListOf()
                }
            }
        }
        if (currentMap.isNotEmpty()) maps.add(currentMap)
    }

    val totalScore = maps.sumOf { map ->
        findHorizontalSymmetry(map).let { if (it != 0) 100 * it else findVerticalSymmetry(map) }
    }

    println(totalScore)
}
