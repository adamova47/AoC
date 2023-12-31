import java.io.File

// data class Point(val x: Int, val y: Int)

fun main() {
    val grid = File("day11.txt").readLines().toTypedArray()
    val galaxyPositions = numberGalaxies(grid, 1000000)
    val pathSums = calculatePathSums(galaxyPositions)
    println(pathSums)
}

fun numberGalaxies(grid: Array<String>, expandFactor: Int): Map<Int, Point> {
    val galaxyPositions = mutableMapOf<Int, Point>()
    var galaxyNumber = 1

    val emptyRows = mutableListOf<Int>()
    val emptyCols = mutableListOf<Int>()

    for (i in grid.indices) {
        if (grid[i].indexOf('#') == -1) {
            emptyRows.add(i)
        }
    }
    for (j in 0 until grid[0].length) {
        var colEmpty = true
        for (i in grid.indices) {
            if (grid[i][j] == '#') {
                colEmpty = false
                break
            }
        }
        if (colEmpty) {
            emptyCols.add(j)
        }
    }

    grid.forEachIndexed { y, row ->
        row.forEachIndexed { x, char ->
            if (char == '#') {
                val newY = y + emptyRows.count { it < y } * (expandFactor - 1)
                val newX = x + emptyCols.count { it < x } * (expandFactor - 1)
                galaxyPositions[galaxyNumber] = Point(newX, newY)
                galaxyNumber++
            }
        }
    }

    return galaxyPositions
}

fun calculatePathSums(galaxyPositions: Map<Int, Point>): Long {
    var pathSums = 0L

    galaxyPositions.forEach { (_, pos1) ->
        galaxyPositions.forEach { (_, pos2) ->
            if (pos1 != pos2) {
                val pathLength = Math.abs(pos1.x - pos2.x).toLong() + Math.abs(pos1.y - pos2.y).toLong()
                pathSums += pathLength
            }
        }
    }

    return pathSums / 2
}

