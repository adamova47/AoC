import java.io.File

fun hashAlgorithm(s: String): Int {
    var currentValue = 0
    for (char in s) {
        val asciiCode = char.toInt()
        currentValue += asciiCode
        currentValue *= 17
        currentValue %= 256
    }
    return currentValue
}

fun main() {
    val filePath = "day15.txt"
    var sumOfResults = 0

    File(filePath).bufferedReader().use { reader ->
        reader.forEachLine { line ->
            val steps = line.split(",")
            steps.map { hashAlgorithm(it) }.sum().let {
                sumOfResults += it
            }
        }
    }

    println(sumOfResults)
}
