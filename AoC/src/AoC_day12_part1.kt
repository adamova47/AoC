import java.io.File

fun main() {
    val puzzleInput = File("day12.txt").readLines()
    val totalArrangements = puzzleInput.sumOf { countArrangements(it) }
    println(totalArrangements)
}

fun countArrangements(springRow: String): Int {
    val (springs, groupsStr) = springRow.trim().split(" ")
    val groups = groupsStr.split(",").map { it.toInt() }

    fun isValid(combination: List<Char>, groups: List<Int>): Boolean {
        val groupSizes = mutableListOf<Int>()
        var count = 0
        for (spring in combination) {
            if (spring == '#') {
                count++
            } else if (count > 0) {
                groupSizes.add(count)
                count = 0
            }
        }
        if (count > 0) groupSizes.add(count)

        return groupSizes == groups
    }

    fun generateCombinations(index: Int, current: MutableList<Char>): Int {
        if (index == springs.length) {
            return if (isValid(current, groups)) 1 else 0
        }

        if (springs[index] != '?') {
            return generateCombinations(index + 1, current.apply { add(springs[index]) })
        }

        return generateCombinations(index + 1, ArrayList(current).apply { add('.') }) +
                generateCombinations(index + 1, ArrayList(current).apply { add('#') })
    }

    return generateCombinations(0, mutableListOf())
}