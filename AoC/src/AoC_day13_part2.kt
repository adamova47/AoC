//import java.io.File
//
//fun findHorizontalSymmetry(map: List<String>): Int {
//    val maxAllowedMismatches = 1
//
//    for (lineIndex in 0 until map.size - 1) {
//        var mismatches = 0
//        var upperIndex = lineIndex
//        var lowerIndex = lineIndex + 1
//
//        while (mismatches <= maxAllowedMismatches && upperIndex >= 0 && lowerIndex < map.size) {
//            mismatches += countMismatchesInRow(map, upperIndex, lowerIndex)
//            if (mismatches > maxAllowedMismatches) break
//
//            upperIndex--
//            lowerIndex++
//        }
//
//        if (mismatches == maxAllowedMismatches) {
//            return lineIndex + 1
//        }
//    }
//    return 0
//}
//
//private fun countMismatchesInRow(map: List<String>, upperIndex: Int, lowerIndex: Int): Int {
//    return map[0].indices.count { columnIndex ->
//        map[upperIndex][columnIndex] != map[lowerIndex][columnIndex]
//    }
//}
//
//fun findVerticalSymmetry(map: List<String>): Int {
//    val transposedMap = map[0].indices.map { col ->
//        map.joinToString("") { it[col].toString() }
//    }
//    return findHorizontalSymmetry(transposedMap)
//}
//
//fun main() {
//    val maps = File("day13.txt").useLines { lines ->
//        lines.fold(mutableListOf<MutableList<String>>()) { acc, line ->
//            if (line.isEmpty()) acc.add(mutableListOf())
//            else acc.lastOrNull()?.add(line) ?: acc.add(mutableListOf(line))
//            acc
//        }.filter { it.isNotEmpty() }
//    }
//
//    val totalScore = maps.sumOf { currentMap ->
//        val horizontalSymmetryScore = findHorizontalSymmetry(currentMap)
//        if (horizontalSymmetryScore != 0) 100 * horizontalSymmetryScore else findVerticalSymmetry(currentMap)
//    }
//
//    println(totalScore)
//}
//
