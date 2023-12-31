import java.io.File

fun is_valid_move(x: Int, y: Int, mapData: List<String>, visited: Array<BooleanArray>): Boolean {
    val maxX = mapData.size
    val maxY = mapData[0].length
    return x in 0 until maxX && y in 0 until maxY && mapData[x][y] != '#' && !visited[x][y]
}

fun get_next_position(x: Int, y: Int, direction: Char): Pair<Int, Int> {
    return when (direction) {
        '^' -> Pair(x - 1, y)
        '>' -> Pair(x, y + 1)
        'v' -> Pair(x + 1, y)
        '<' -> Pair(x, y - 1)
        else -> Pair(x, y)
    }
}

fun dfs(x: Int, y: Int, mapData: List<String>, visited: Array<BooleanArray>, currentLength: Int, maxLength: Int): Int {
    var maxLen = maxLength
    if (x == mapData.size - 1) {
        return maxOf(currentLength, maxLength)
    }

    visited[x][y] = true
    val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

    for ((dx, dy) in directions) {
        val nx = x + dx
        val ny = y + dy

        if (is_valid_move(nx, ny, mapData, visited)) {
            var nextX = nx
            var nextY = ny

            if (mapData[x][y] in listOf('^', '>', 'v', '<')) {
                val nextPos = get_next_position(x, y, mapData[x][y])
                nextX = nextPos.first
                nextY = nextPos.second
            }

            if (is_valid_move(nextX, nextY, mapData, visited)) {
                maxLen = dfs(nextX, nextY, mapData, visited, currentLength + 1, maxLen)
            }
        }
    }

    visited[x][y] = false
    return maxLen
}

fun main() {
    val mapData = File("day23.txt").readLines()

    val startRow = 0
    val startCol = mapData[0].indexOf('.')

    val visited = Array(mapData.size) { BooleanArray(mapData[0].length) }

    val longestHike = dfs(startRow, startCol, mapData, visited, 0, 0)
    println(longestHike)
}
