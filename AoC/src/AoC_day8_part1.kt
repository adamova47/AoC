import java.io.File

fun main() {
    val fileContent = File("day8.txt").readText()
    val (instructions, nodeDefinitions) = fileContent.split("\n", limit = 2)
    val nodes = parseNodeDefinitions(nodeDefinitions.trim())
    val stepsToZZZ = navigateNodes("AAA", instructions.trim(), nodes)
    println(stepsToZZZ)
}

fun parseNodeDefinitions(nodeDefinitions: String): Map<String, Pair<String, String>> {
    return nodeDefinitions.lines().associate { line ->
        val (node, paths) = line.split(" = ")
        val (left, right) = paths.removeSurrounding("(", ")").split(", ")
        node to (left to right)
    }
}

fun navigateNodes(startNode: String, instructions: String, nodes: Map<String, Pair<String, String>>): Int {
    var currentNode = startNode
    var steps = 0
    var instructionIndex = 0

    while (currentNode != "ZZZ") {
        val nextDirection = instructions[instructionIndex]
        currentNode = if (nextDirection == 'L') nodes[currentNode]!!.first else nodes[currentNode]!!.second
        instructionIndex = (instructionIndex + 1) % instructions.length
        steps++
    }

    return steps
}
