import java.io.File

fun main() {
    val lines = File("day19.txt").readLines()
    val splitIndex = lines.indexOfFirst { it.isBlank() }
    val workflows = parseWorkflows(lines.subList(0, splitIndex))
    val parts = parseParts(lines.subList(splitIndex + 1, lines.size))
    val totalRating = parts.filter { processPart(it, workflows) }.sumOf { it.values.sum() }
    println(totalRating)
}

fun parseWorkflows(input: List<String>): Map<String, List<Pair<String, String?>>> {
    return input.associate { line ->
        val parts = line.split("{", "}")
        val workflowName = parts[0]
        val rules = parts[1].split(",").map {
            val ruleParts = it.split(":")
            ruleParts[0] to ruleParts.getOrNull(1)
        }
        workflowName to rules
    }
}

fun parseParts(input: List<String>): List<Map<String, Int>> {
    return input.map { line ->
        line.removeSurrounding("{", "}").split(",").associate {
            val (key, value) = it.split("=")
            key to value.toInt()
        }
    }
}

fun processPart(part: Map<String, Int>, workflows: Map<String, List<Pair<String, String?>>>): Boolean {
    var currentWorkflow = "in"
    while (true) {
        for ((condition, action) in workflows[currentWorkflow] ?: emptyList()) {
            if (action == "A") return true
            if (action == "R") return false
            if (condition.isNotBlank() && evaluateCondition(part, condition)) {
                currentWorkflow = action ?: break
                break
            }
        }
    }
}

fun evaluateCondition(part: Map<String, Int>, condition: String): Boolean {
    if (!condition.contains("<") && !condition.contains(">")) {
        return false
    }

    val (attr, valueStr) = if ("<" in condition) {
        condition.split("<")
    } else {
        condition.split(">")
    }

    val partValue = part[attr] ?: return false
    val value = valueStr.toIntOrNull() ?: return false

    return if ("<" in condition) {
        partValue < value
    } else {
        partValue > value
    }
}
