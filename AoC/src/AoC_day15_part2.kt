import java.io.File

class Lens(val label: String, private val focalLength: Int) {
    fun focusingPower(boxNumber: Int, slotNumber: Int): Int {
        return (boxNumber + 1) * slotNumber * focalLength
    }
}

class Box {
    private val lenses = mutableListOf<Lens>()

    fun addOrReplaceLens(lens: Lens) {
        val existingLensIndex = lenses.indexOfFirst { it.label == lens.label }
        if (existingLensIndex != -1) {
            lenses[existingLensIndex] = lens
        } else {
            lenses.add(lens)
        }
    }

    fun removeLens(label: String) {
        lenses.removeAll { it.label == label }
    }

    fun focusingPower(boxNumber: Int): Int {
        return lenses.mapIndexed { index, lens -> lens.focusingPower(boxNumber, index + 1) }.sum()
    }
}

/*
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
 */

fun processSequence(sequence: List<String>): List<Box> {
    val boxes = Array(256) { Box() }
    for (step in sequence) {
        val parts = step.split("=", "-")
        val label = parts[0]
        val boxNumber = hashAlgorithm(label)
        if (step.contains('=')) {
            val focalLength = parts[1].toInt()
            val lens = Lens(label, focalLength)
            boxes[boxNumber].addOrReplaceLens(lens)
        } else {
            boxes[boxNumber].removeLens(label)
        }
    }
    return boxes.toList()
}

fun totalFocusingPower(boxes: List<Box>): Int {
    return boxes.mapIndexed { index, box -> box.focusingPower(index) }.sum()
}

fun main() {
    val filePath = "day15.txt"
    val sequence = File(filePath).readLines().flatMap { it.split(",") }
    val boxes = processSequence(sequence)
    val focusingPower = totalFocusingPower(boxes)
    println(focusingPower)
}
