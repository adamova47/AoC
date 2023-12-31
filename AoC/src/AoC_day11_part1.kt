import java.io.File
import java.util.*

data class Point(val x: Int, val y: Int)

fun main() {
    val grid = File("day11.txt").readLines().toTypedArray()
    val expandedGrid = expandUniverse(grid)
    val galaxyPositions = numberGalaxies(expandedGrid)
    val pathSums = calculatePathSums(expandedGrid, galaxyPositions)
    println(pathSums)
}

fun expandUniverse(grid: Array<String>): Array<Array<Char>> {
    val rows = grid.size
    val cols = grid[0].length
    val emptyRows = mutableSetOf<Int>()
    val emptyCols = mutableSetOf<Int>()

    for (i in 0 until rows) emptyRows.add(i)
    for (j in 0 until cols) emptyCols.add(j)

    grid.forEachIndexed { y, row ->
        row.forEachIndexed { x, char ->
            if (char == '#') {
                emptyRows.remove(y)
                emptyCols.remove(x)
            }
        }
    }

    val expandedGrid = Array(rows + emptyRows.size) { Array(cols + emptyCols.size) { '.' } }

    grid.forEachIndexed { y, row ->
        row.forEachIndexed { x, char ->
            val newY = y + emptyRows.count { it < y }
            val newX = x + emptyCols.count { it < x }
            expandedGrid[newY][newX] = char
        }
    }

    return expandedGrid
}

fun numberGalaxies(grid: Array<Array<Char>>): Map<Int, Point> {
    val galaxyPositions = mutableMapOf<Int, Point>()
    var galaxyNumber = 1

    grid.forEachIndexed { y, row ->
        row.forEachIndexed { x, char ->
            if (char == '#') {
                galaxyPositions[galaxyNumber] = Point(x, y)
                galaxyNumber++
            }
        }
    }

    return galaxyPositions
}

fun calculatePathSums(grid: Array<Array<Char>>, galaxyPositions: Map<Int, Point>): Int {
    var pathSums = 0

    galaxyPositions.forEach { (g1, pos1) ->
        galaxyPositions.forEach { (g2, pos2) ->
            if (g1 < g2) {
                val pathLength = shortestPath(grid, pos1, pos2)
                pathSums += pathLength
            }
        }
    }

    return pathSums
}

fun shortestPath(grid: Array<Array<Char>>, start: Point, end: Point): Int {
    val queue: Queue<Pair<Point, Int>> = LinkedList()
    val visited = mutableSetOf<Point>()
    val directions = arrayOf(Point(0, 1), Point(1, 0), Point(0, -1), Point(-1, 0))

    queue.add(start to 0)

    while (queue.isNotEmpty()) {
        val (current, distance) = queue.remove()

        if (current == end) {
            return distance
        }

        directions.forEach {
            val next = Point(current.x + it.x, current.y + it.y)
            if (next.x in grid[0].indices && next.y in grid.indices && next !in visited) {
                visited.add(next)
                queue.add(next to distance + 1)
            }
        }
    }

    return Int.MAX_VALUE
}
