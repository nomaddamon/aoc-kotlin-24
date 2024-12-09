fun main() {

    /**
     * solves formula recursively, trying operations in order: || => * => +
     */
    fun solveRecursively(result: Long, parts: List<Long>, actions: List<Action>, minIdx: Int): Boolean {
        var res = parts[0]
        if (res == result && actions.isEmpty()) return true
        for (i in 0 until parts.size - 1) {
            if (actions[i] == Action.Multiply) {
                res *= parts[i + 1]
            } else if (actions[i] == Action.Concatenate) {
                res = (res.toString() + parts[i + 1].toString()).toLong()
            } else {
                res += parts[i + 1]
            }
            if (res > result) break
        }

        if (res == result) return true
        if (actions.all { it == Action.Add }) return false

        for (i in minIdx until actions.size) {
            if (actions[i] == Action.Add) continue
            val newActions = actions.toMutableList()
            if (actions[i] == Action.Concatenate) {
                newActions[i] = Action.Multiply
                if (solveRecursively(result, parts, newActions, i + 1)) return true
            }
            newActions[i] = Action.Add
            if (solveRecursively(result, parts, newActions, i + 1)) return true

        }
        return false
    }

    fun solve1(equation: String): Long {
        if (equation.isEmpty()) return 0
        val result = equation.split(":")[0].toLong()
        val parts = equation.split(":")[1].trim().split(" ").map { it.toLong() }
        // for part 1, start with only multiplication actions
        val ok = solveRecursively(result, parts, parts.filterIndexed { i, _ -> i < parts.size - 1 }.map { Action.Multiply }, 0)
        return if (ok) result else 0
    }

    fun solve2(equation: String): Long {
        if (equation.isEmpty()) return 0
        val result = equation.split(":")[0].toLong()
        val parts = equation.split(":")[1].trim().split(" ").map { it.toLong() }
        // for part 2, start with concatenation actions
        val ok = solveRecursively(result, parts, parts.filterIndexed { i, _ -> i < parts.size - 1 }.map { Action.Concatenate }, 0)
        return if (ok) result else 0
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { solve1(it) }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { solve2(it) }
    }

    // check provided examples
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    // actual input
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

enum class Action {
    Multiply,
    Concatenate,
    Add
}