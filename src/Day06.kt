fun main() {
    val guardSet = setOf('^', '>', '<', 'v')

    fun parseInput(input: List<String>): Pair<HashMap<Int, HashMap<Int, Char>>, Guard> {
        val hashMap = HashMap<Int, HashMap<Int, Char>>()
        val guard = Guard(0, 0, Direction.NORTH)
        for (i in input.indices) {
            hashMap[i] = HashMap()
            for (j in input[i].indices) {
                hashMap[i]!![j] = input[i][j]
                if (guardSet.contains(input[i][j])) {
                    guard.x = j
                    guard.y = i
                    guard.d = when (input[i][j]) {
                        '^' -> Direction.NORTH
                        '>' -> Direction.EAST
                        '<' -> Direction.WEST
                        else -> Direction.SOUTH
                    }
                    hashMap[i]!![j] = '.'
                }
            }
        }
        return hashMap to guard
    }

    fun part1(input: List<String>): Int {
        val (map, guard) = parseInput(input)
        val visited = HashSet<String>()
        //walk the map
        var state = GuardState.MOVING
        while (state == GuardState.MOVING) {
            state = guard.move(map, visited)
        }

        if (state == GuardState.LOOP) return 0

        val result = map.values.sumOf { r -> r.values.count { c -> c == 'X' } }

        return result
    }

    fun part2(input: List<String>): Int {
        val (intialMap, initialGuard) = parseInput(input)
        var loops = 0
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (guardSet.contains(input[i][j]) || input[i][j] == '#') continue
                val guard=Guard(initialGuard.x, initialGuard.y, initialGuard.d)
                val map = HashMap<Int, HashMap<Int, Char>>()
                for (mi in input.indices) {
                    map[mi] = HashMap()
                    for (mj in input[i].indices) {
                        map[mi]!![mj] = intialMap[mi]!![mj]!!
                    }
                }

                map[i]!![j] = '#'
                val visited = HashSet<String>()
                //walk the map
                var state = GuardState.MOVING
                while (state == GuardState.MOVING) {
                    state = guard.move(map, visited)
                }
                if (state == GuardState.LOOP) {
                    loops++
                }
            }
        }
        return loops
    }

    // check provided examples
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    // actual input
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

class Guard(var x: Int, var y: Int, var d: Direction) {
    fun move(map: HashMap<Int, HashMap<Int, Char>>, visited: HashSet<String>): GuardState {
        // mark cell as visited
        map[y]!![x] = 'X'
        visited.add(this.toString())
        //move
        val nextX = when (d) {
            Direction.EAST -> x + 1
            Direction.WEST -> x - 1
            else -> x
        }
        val nextY = when (d) {
            Direction.NORTH -> y - 1
            Direction.SOUTH -> y + 1
            else -> y
        }
        // exit when over edge
        if (nextX < 0 || nextX == map[0]!!.size || nextY < 0 || nextY == map.size) return GuardState.EXITED

        // turn when needed
        val nextD = if (map[nextY]!![nextX] == '#') {
            when (d) {
                Direction.NORTH -> Direction.EAST
                Direction.SOUTH -> Direction.WEST
                Direction.EAST -> Direction.SOUTH
                Direction.WEST -> Direction.NORTH
            }
        } else {
            d
        }

        // update state
        if (d != nextD) {
            d = nextD
        } else {
            x = nextX
            y = nextY
        }

        if (visited.contains(this.toString())) return GuardState.LOOP

        return GuardState.MOVING
    }

    override fun toString(): String = "$x|$y|$d"
}

enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

enum class GuardState {
    MOVING, EXITED, LOOP
}
