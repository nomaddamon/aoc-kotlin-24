fun main() {

    fun solveRecursively(map: HashMap<Int, HashMap<Int, Int>>, position: Pair<Int, Int>, visited: HashMap<Pair<Int, Int>, HashSet<Pair<Int, Int>>>, searchValue: Int): HashSet<Pair<Int, Int>> {
        if (map[position.first]!![position.second] != searchValue) return hashSetOf()
        if (visited.containsKey(position) && map[position.first]!![position.second] == searchValue) return visited[position]!!

        if (searchValue == 9) {
            return hashSetOf(position)
        }

        val result = HashSet<Pair<Int, Int>>()
        if (position.first > 0) result.addAll(solveRecursively(map, position.first - 1 to position.second, visited, searchValue + 1))
        if (position.second > 0) result.addAll(solveRecursively(map, position.first to position.second - 1, visited, searchValue + 1))
        if (position.first < map.size - 1) result.addAll(solveRecursively(map, position.first + 1 to position.second, visited, searchValue + 1))
        if (position.second < map[0]!!.size - 1) result.addAll(solveRecursively(map, position.first to position.second + 1, visited, searchValue + 1))

        visited[position] = result
        return result
    }

    fun solveRecursively2(map: HashMap<Int, HashMap<Int, Int>>, position: Pair<Int, Int>, visited: HashMap<Pair<Int, Int>, Int>, searchValue: Int): Int {
        if (map[position.first]!![position.second] != searchValue) return 0
        if (visited.containsKey(position) && map[position.first]!![position.second] == searchValue) return visited[position]!!

        if (searchValue == 9) {
            return 1
        }

        var result = 0
        if (position.first > 0) result += solveRecursively2(map, position.first - 1 to position.second, visited, searchValue + 1)
        if (position.second > 0) result += solveRecursively2(map, position.first to position.second - 1, visited, searchValue + 1)
        if (position.first < map.size - 1) result += solveRecursively2(map, position.first + 1 to position.second, visited, searchValue + 1)
        if (position.second < map[0]!!.size - 1) result += solveRecursively2(map, position.first to position.second + 1, visited, searchValue + 1)

        visited[position] = result
        return result
    }

    /**
     * parses input to XY map
     */
    fun parseInput(input: List<String>): Pair<HashMap<Int, HashMap<Int, Int>>, List<Pair<Int, Int>>> {
        val hashMap = HashMap<Int, HashMap<Int, Int>>()
        val startPositions: MutableList<Pair<Int, Int>> = mutableListOf()
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (i == 0) hashMap[j] = HashMap()
                hashMap[j]!![i] = input[i][j].code - 48  // x-y
                if (hashMap[j]!![i] == 0) startPositions.add(j to i)
            }
        }
        return hashMap to startPositions
    }

    fun part1(input: List<String>): Int {

        val mapAndStart = parseInput(input)
        val visited = HashMap<Pair<Int, Int>, HashSet<Pair<Int, Int>>>()
        return mapAndStart.second.sumOf { solveRecursively(mapAndStart.first, it, visited, 0).size }
    }


    fun part2(input: List<String>): Int {
        val mapAndStart = parseInput(input)
        val visited = HashMap<Pair<Int, Int>, Int>()
        return mapAndStart.second.sumOf { solveRecursively2(mapAndStart.first, it, visited, 0) }
    }

    // check provided examples
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)

    // actual input
    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
