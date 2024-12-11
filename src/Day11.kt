import kotlin.math.abs
import kotlin.math.log10

fun main() {

    fun Long.countDigits() = when (this) {
        0L -> 1
        else -> log10(abs(toDouble())).toInt() + 1
    }

    /**
     * mutates a single iteration of given list
     */
    fun mutateList(list: List<Long>): List<Long> {
        val result: MutableList<Long> = mutableListOf()
        for (i in list.indices) {
            if (list[i] == 0L) result.add(1)
            else if (list[i].countDigits() % 2 == 0) {
                val elementStr = list[i].toString()
                try {
                    result.add(elementStr.substring(0 until elementStr.count() / 2).toLong())
                    result.add(elementStr.substring(elementStr.count() / 2).toLong())
                } catch (e: Exception) {
                    println("Error: $elementStr")
                }
            } else {
                result.add(list[i] * 2024)
            }
        }
        return result
    }

    val resultCache = HashMap<Int, HashMap<Long, Long>>()

    /**
     * mutates list recursively, until iterationsRemaining reaches zero
     * caches results
     */
    fun mutateListRecursive(list: List<Long>, iterationsRemaining: Int): Long {
        if (iterationsRemaining == 0) return list.size.toLong()

        var res = 0L
        for (element in list) {
            if (resultCache[iterationsRemaining] != null && resultCache[iterationsRemaining]!![element] != null) {
                res += resultCache[iterationsRemaining]!![element]!!
            } else {
                val r = mutateListRecursive(mutateList(listOf(element)), iterationsRemaining - 1)
                if (resultCache[iterationsRemaining] == null) resultCache[iterationsRemaining] = HashMap()
                resultCache[iterationsRemaining]!![element] = r
                res += r
            }
        }

        return res
    }

    fun part1(input: List<String>): Long {
        val list = input[0].split(' ').filter { it.isNotEmpty() }.map { it.toLong() }
        val res = mutateListRecursive(list, 25)
        return res
    }

    fun part2(input: List<String>): Long {
        val list = input[0].split(' ').filter { it.isNotEmpty() }.map { it.toLong() }
        val res = mutateListRecursive(list, 75)
        return res
    }

    // check provided examples
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 55312L)

    // actual input
    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
