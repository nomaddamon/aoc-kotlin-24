import kotlin.math.abs

fun main() {
    fun parseStoredLists(input: List<String>) =
        input.map { le -> le.split(" ").filter { it.isNotEmpty() }.let { Pair(it[0].toInt(), it[1].toInt()) } }
            .unzip()
            .let { (firstList, secondList) -> Pair(firstList.sorted(), secondList.sorted()) }

    fun part1(input: List<String>): Int {
        val (firstList, secondList) = parseStoredLists(input)

        var distance = 0
        firstList.forEachIndexed { index, i -> distance += abs(i - secondList[index]) }
        return distance
    }

    fun part2(input: List<String>): Int {
        val (firstList, secondList) = parseStoredLists(input)

        val similarityMap = secondList.groupBy { it }.map { it.key to it.value.size }.toMap()
        return firstList.sumOf { it * (similarityMap[it] ?: 0) }
    }

    // simple tests
    check(part1(listOf("1 2", "2 3")) == 2)
    check(part2(listOf("1 2", "2 3", "4 2")) == 4)

    // check provided examples
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // actual input
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
