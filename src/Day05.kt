import kotlin.math.floor

fun main() {

    /**
     * parses input to rules and pages
     */
    fun parseInput(input: List<String>): Pair<Map<Int, List<Int>>, List<List<Int>>> {
        // parse input
        var rules = true
        val ruleMap: MutableMap<Int, MutableList<Int>> = mutableMapOf()
        val manualList: MutableList<List<Int>> = mutableListOf()
        for (line in input) {
            if (line.isEmpty()) {
                rules = false
                continue
            }
            if (rules) {
                val (first, second) = line.split("|").map { it.toInt() }
                if (!ruleMap.containsKey(first)) ruleMap[first] = mutableListOf()
                ruleMap[first]!!.add(second)
            } else {
                manualList.add(line.split(",").map { it.toInt() })
            }
        }
        return ruleMap to manualList
    }

    /**
     * validates a single manual against the rules, on failure also returns first error location (pair of indexes)
     */
    fun isValid(manual: List<Int>, ruleMap: Map<Int, List<Int>>): Pair<Boolean, Pair<Int, Int>?> {
        for (i in manual.indices) {
            val ruleList = ruleMap[manual[i]]
            if (ruleList.isNullOrEmpty()) {
                continue
            }
            for (rule in ruleList) {
                val conflictingPageIndex = manual.filterIndexed { index, _ -> index < i }
                    .mapIndexed { index, manualPage -> Pair(index, manualPage) }
                    .firstOrNull { it.second == rule }?.first
                if (conflictingPageIndex != null) {
                    return false to Pair(i, conflictingPageIndex)
                }
            }
        }
        return true to null
    }

    /**
     * validates all manuals against all rules, returns 2 collections - valid- and invalid manuals
     */
    fun validateManuals(
        ruleMap: Map<Int, List<Int>>,
        manualList: List<List<Int>>
    ): Pair<List<List<Int>>, List<List<Int>>> {
        val validManuals: MutableList<List<Int>> = mutableListOf()
        val invalidManuals: MutableList<List<Int>> = mutableListOf()
        for (manual in manualList) {
            if (isValid(manual, ruleMap).first) {
                validManuals.add(manual)
            } else {
                invalidManuals.add(manual)
            }
        }
        return validManuals to invalidManuals
    }

    /**
     * swaps 2 elements in mutable list
     */
    fun swapElements(list: MutableList<Int>, i1: Int, i2: Int): MutableList<Int> {
        val t = list[i1]
        list[i1] = list[i2]
        list[i2] = t
        return list
    }

    /**
     * makes a manual adhere to rules
     */
    fun fixManual(invalidManual: List<Int>, ruleMap: Map<Int, List<Int>>): List<Int> {
        var manual = invalidManual.toMutableList()
        var sanityCheck = 0
        while (sanityCheck++ < 1000000) {
            val (ok, problem) = isValid(manual, ruleMap)
            if (ok || problem == null) return manual
            manual = swapElements(manual, problem.first, problem.second)
        }
        throw IllegalArgumentException("failed to fix manual")
    }

    fun part1(input: List<String>): Int {
        val (ruleMap, manualList) = parseInput(input)
        val (validManuals, _) = validateManuals(ruleMap, manualList)
        return validManuals.sumOf { it[floor(it.size / 2.0).toInt()] }
    }

    fun part2(input: List<String>): Int {
        val (ruleMap, manualList) = parseInput(input)
        val (_, invalidManuals) = validateManuals(ruleMap, manualList)
        val fixedManuals = invalidManuals.map { fixManual(it, ruleMap) }
        return fixedManuals.sumOf { it[floor(it.size / 2.0).toInt()] }
    }

    // check provided examples
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    // actual input
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
