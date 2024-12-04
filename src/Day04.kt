fun main() {

    fun solutionCount1(map: Map<Int, Map<Int, Char>>, rowIndex: Int, colIndex: Int): Int {
        if (map[rowIndex]!![colIndex] != 'X') return 0
        // row-col is location of X
        // 8 possible direction starting from X
        val okRight = colIndex < map[rowIndex]!!.size - 3
        val okLeft = colIndex >= 3
        val okDown = map.size > rowIndex + 3
        val okUp = rowIndex >= 3
        var res = 0
        // XMAS
        if (okRight && map[rowIndex]!![colIndex + 1] == 'M' && map[rowIndex]!![colIndex + 2] == 'A' && map[rowIndex]!![colIndex + 3] == 'S') res++
        // SAMX
        if (okLeft && map[rowIndex]!![colIndex - 1] == 'M' && map[rowIndex]!![colIndex - 2] == 'A' && map[rowIndex]!![colIndex - 3] == 'S') res++
        // X
        // M
        // A
        // S
        if (okDown && map[rowIndex + 1]!![colIndex] == 'M' && map[rowIndex + 2]!![colIndex] == 'A' && map[rowIndex + 3]!![colIndex] == 'S') res++
        // S
        // A
        // M
        // X
        if (okUp && map[rowIndex - 1]!![colIndex] == 'M' && map[rowIndex - 2]!![colIndex] == 'A' && map[rowIndex - 3]!![colIndex] == 'S') res++
        // X
        //  M
        //   A
        //    S
        if (okDown && okRight && map[rowIndex + 1]!![colIndex + 1] == 'M' && map[rowIndex + 2]!![colIndex + 2] == 'A' && map[rowIndex + 3]!![colIndex + 3] == 'S') res++
        //    X
        //   M
        //  A
        // S
        if (okDown && okLeft && map[rowIndex + 1]!![colIndex - 1] == 'M' && map[rowIndex + 2]!![colIndex - 2] == 'A' && map[rowIndex + 3]!![colIndex - 3] == 'S') res++
        //    S
        //   A
        //  M
        // X
        if (okUp && okRight && map[rowIndex - 1]!![colIndex + 1] == 'M' && map[rowIndex - 2]!![colIndex + 2] == 'A' && map[rowIndex - 3]!![colIndex + 3] == 'S') res++
        // S
        //  A
        //   M
        //    X
        if (okUp && okLeft && map[rowIndex - 1]!![colIndex - 1] == 'M' && map[rowIndex - 2]!![colIndex - 2] == 'A' && map[rowIndex - 3]!![colIndex - 3] == 'S') res++
        return res
    }

    fun solutionCount2(map: Map<Int, Map<Int, Char>>, rowIndex: Int, colIndex: Int): Int {
        if (map[rowIndex]!![colIndex] != 'M') return 0
        // row-col is location of M
        // 4 possible direction starting from M
        val okRight = colIndex < map[rowIndex]!!.size - 2
        val okLeft = colIndex >= 2
        val okDown = map.size > rowIndex + 2
        val okUp = rowIndex >= 2
        var res = 0
        // M.S
        // .A.
        // M.S
        if (okRight && okDown && map[rowIndex]!![colIndex + 2] == 'S' && map[rowIndex + 1]!![colIndex + 1] == 'A' && map[rowIndex + 2]!![colIndex] == 'M' && map[rowIndex + 2]!![colIndex + 2] == 'S') res++
        // M.M
        // .A.
        // S.S
        if (okRight && okDown && map[rowIndex]!![colIndex + 2] == 'M' && map[rowIndex + 1]!![colIndex + 1] == 'A' && map[rowIndex + 2]!![colIndex] == 'S' && map[rowIndex + 2]!![colIndex + 2] == 'S') res++
        // S.S
        // .A.
        // M.M
        if (okRight && okUp && map[rowIndex]!![colIndex + 2] == 'M' && map[rowIndex - 1]!![colIndex + 1] == 'A' && map[rowIndex - 2]!![colIndex] == 'S' && map[rowIndex - 2]!![colIndex + 2] == 'S') res++
        // S.M
        // .A.
        // S.M
        if (okDown && okLeft && map[rowIndex]!![colIndex - 2] == 'S' && map[rowIndex + 1]!![colIndex - 1] == 'A' && map[rowIndex + 2]!![colIndex - 2] == 'S' && map[rowIndex + 2]!![colIndex] == 'M') res++
        return res
    }

    fun part1(input: List<String>): Int {
        val map = input.mapIndexed { rowIndex, inp ->
            Pair(
                rowIndex,
                inp.mapIndexed { index, s -> Pair(index, s) }.toMap()
            )
        }.toMap()

        return map.map { (rowIndex, row) -> row.map { (colIndex, _) -> solutionCount1(map, rowIndex, colIndex) }.sum() }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val map = input.mapIndexed { rowIndex, inp ->
            Pair(
                rowIndex,
                inp.mapIndexed { index, s -> Pair(index, s) }.toMap()
            )
        }.toMap()

        return map.map { (rowIndex, row) -> row.map { (colIndex, _) -> solutionCount2(map, rowIndex, colIndex) }.sum() }
            .sum()
    }

    // check edges
    check(
        part1(
            listOf(
                "XMAS",
                "MM..",
                "A.A.",
                "S..S"
            )
        ) == 3
    )

    check(
        part1(
            listOf(
                "SAMX",
                "..MM",
                ".A.A",
                "S..S"
            )
        ) == 3
    )

    check(
        part1(
            listOf(
                "S..S",
                ".A.A",
                "..MM",
                "SAMX"
            )
        ) == 3
    )

    check(
        part1(
            listOf(
                "S..S",
                "A.A.",
                "MM..",
                "XMAS"
            )
        ) == 3
    )

    // check provided examples
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // actual input
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
