import java.io.File

fun main() {
    val puzzleInput = File("day12.txt").readLines()
    val totalArrangements = puzzleInput.sumOf { unfolPuzzleInput(it) }
    println(totalArrangements)
}

fun unfolPuzzleInput(row: String): Long {
    val (springs, groupsStr) = row.trim().split(" ")
    val unfoldedSprings = (1..5).joinToString("?") { springs }
    val numbers = (1..5).flatMap { groupsStr.split(",").map(String::toInt) }

    return countArrangements(unfoldedSprings, numbers)
}

fun countArrangements(text: String, numbers: List<Int>): Long {
    val states = buildStatesString(numbers)
    var stateCounts = mutableMapOf(0 to 1L)

    text.forEach { char ->
        stateCounts = updateStateCounts(stateCounts, states, char)
    }

    return finalCount(stateCounts, states.length)
}

private fun buildStatesString(numbers: List<Int>): String {
    return numbers.joinToString(separator = ".", prefix = ".", postfix = ".") { "#".repeat(it) }
}

private fun updateStateCounts(currentCounts: Map<Int, Long>, states: String, char: Char): MutableMap<Int, Long> {
    val newCounts = mutableMapOf<Int, Long>()

    currentCounts.forEach { (state, count) ->
        when (char) {
            '?' -> handleQuestionMark(state, count, states, newCounts)
            '.' -> handleDot(state, count, states, newCounts)
            '#' -> handleHash(state, count, states, newCounts)
        }
    }

    return newCounts
}

private fun handleQuestionMark(state: Int, count: Long, states: String, counts: MutableMap<Int, Long>) {
    incrementCount(counts, state, count, condition = { states[state] == '.' })
    incrementCount(counts, state + 1, count, condition = { state + 1 < states.length })
}

private fun handleDot(state: Int, count: Long, states: String, counts: MutableMap<Int, Long>) {
    incrementCount(counts, state, count, condition = { states[state] == '.' })
    incrementCount(counts, state + 1, count, condition = { state + 1 < states.length && states[state + 1] == '.' })
}

private fun handleHash(state: Int, count: Long, states: String, counts: MutableMap<Int, Long>) {
    incrementCount(counts, state + 1, count, condition = { state + 1 < states.length && states[state + 1] == '#' })
}

private fun incrementCount(counts: MutableMap<Int, Long>, state: Int, count: Long, condition: () -> Boolean) {
    if (condition()) {
        counts[state] = counts.getOrDefault(state, 0L) + count
    }
}

private fun finalCount(stateCounts: Map<Int, Long>, statesLength: Int): Long {
    return stateCounts.getOrDefault(statesLength - 1, 0L) + stateCounts.getOrDefault(statesLength - 2, 0L)
}
