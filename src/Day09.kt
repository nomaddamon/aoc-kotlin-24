fun main() {

    /**
     * swaps 2 elements in mutable list
     */
    fun swapElements(list: MutableList<Long>, i1: Int, i2: Int): MutableList<Long> {
        val t = list[i1]
        list[i1] = list[i2]
        list[i2] = t
        return list
    }

    fun parseInput(input: List<String>): MutableList<Long> {
        val inp = input.first().map { it.code - '0'.code }
        var fid = 0L
        var file = true
        return buildList {
            for (i in inp.indices) {
                for (j in 0 until inp[i]) {
                    if (file) {
                        add(fid)
                    } else {
                        add(-1)
                    }
                }
                if (file) fid++
                file = !file
            }
        }.toMutableList()
    }

    fun part1(input: List<String>): Long {
        var list = parseInput(input)

        //defrag
        for (i in list.size - 1 downTo 0) {
            if (list[i] >= 0) {
                val firstFreePos = list.indexOfFirst { it < 0 }
                if (firstFreePos > i) break
                list = swapElements(list, i, firstFreePos)
            }
        }

        // checkusm
        return list.mapIndexed { index, i -> if (i >= 0) index * i else 0 }.sum()
    }


    fun part2(input: List<String>): Long {
        var list = parseInput(input)
        val processed = HashSet<Long>()

        //defrag
        for (i in list.size - 1 downTo 0) {
            if (list[i] >= 0 && !processed.contains(list[i])) {
                processed.add(list[i])
                // size
                val firstIndex = list.indexOfFirst { it == list[i] }
                val size = i - firstIndex + 1

                val freeIdx = list.filterIndexed { ix, _ -> ix < i }.mapIndexed { idx, l -> Pair(idx, l) }
                    .filterIndexed { index, l -> l.second < 0 && (1 until size).all { index + it < list.size && list[index + it] < 0 } }.firstOrNull()?.first
                if (freeIdx != null) {
                    for (j in 0 until size) {
                        list = swapElements(list, firstIndex + j, freeIdx + j)
                    }
                }
            }
        }

        // checkusm
        return list.mapIndexed { index, i -> if (i >= 0) index * i else 0 }.sum()
    }

    // check provided examples
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    // actual input
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
