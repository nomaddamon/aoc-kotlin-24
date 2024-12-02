import kotlin.math.abs

fun main() {
    fun levelsOk(levels: List<Int>): Boolean {
        val levelDeltas = levels
            .filterIndexed { index, _ -> (index < levels.size - 1) }
            .mapIndexed { index, i -> i - levels[index + 1] }
        return levelDeltas.all { abs(it) in 1..3 } && abs(levelDeltas.sum()) == levelDeltas.sumOf { ld -> abs(ld) }
    }

    fun part1(input: List<String>) =
        input.count { inp ->
            val levels = inp.split(" ").filter { l -> l.isNotEmpty() }.map { le -> le.toInt() }
            levelsOk(levels)
        }

    fun part2(input: List<String>): Int {
        return input.count { inp ->
            val levels = inp.split(" ").filter { l -> l.isNotEmpty() }.map { le -> le.toInt() }.toMutableList()
            if (levelsOk(levels)) return@count true
            // try removing each element
            for (i in 0 until levels.size) {
                if (levelsOk(levels.filterIndexed { index, _ -> index != i })) return@count true
            }
            false
        }
    }

    // simple tests
    check(part1(listOf("1 2 2", "2 3", "4 1", "4 0")) == 2)
    check(part2(listOf("1 2 2", "2 3", "4 1", "4 0 0")) == 3)

    // check provided examples
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // actual input
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
