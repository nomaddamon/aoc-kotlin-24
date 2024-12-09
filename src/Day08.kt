fun main() {

    fun part1(input: List<String>): Int {

        val hashMap = HashMap<Char, MutableList<Pair<Int, Int>>>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] != '.') {
                    if (!hashMap.containsKey(input[i][j])) hashMap[input[i][j]] = mutableListOf()
                    hashMap[input[i][j]]!!.add(j to i)
                }
            }
        }
        val antiNodeMap = HashSet<Pair<Int, Int>>()

        for (entry in hashMap) {
            for (i in 0 until entry.value.size) {
                for (j in i + 1 until entry.value.size) {
                    val aNode1 = Pair(entry.value[i].first + (entry.value[i].first - entry.value[j].first), entry.value[i].second + (entry.value[i].second - entry.value[j].second))
                    val aNode2 = Pair(entry.value[j].first + (entry.value[j].first - entry.value[i].first), entry.value[j].second + (entry.value[j].second - entry.value[i].second))
                    if (aNode1.first >= 0 && aNode1.first < input[0].length && aNode1.second >= 0 && aNode1.second < input.size) antiNodeMap.add(aNode1)
                    if (aNode2.first >= 0 && aNode2.first < input[0].length && aNode2.second >= 0 && aNode2.second < input.size) antiNodeMap.add(aNode2)
                }
            }
        }

        return antiNodeMap.size
    }

    fun part2(input: List<String>): Int {

        val hashMap = HashMap<Char, MutableList<Pair<Int, Int>>>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] != '.') {
                    if (!hashMap.containsKey(input[i][j])) hashMap[input[i][j]] = mutableListOf()
                    hashMap[input[i][j]]!!.add(j to i)
                }
            }
        }
        val antiNodeMap = HashSet<Pair<Int, Int>>()

        for (entry in hashMap) {
            for (i in 0 until entry.value.size) {
                for (j in i + 1 until entry.value.size) {
                    var mult = 0
                    while (mult >= 0) {
                        val aNode1 = Pair(entry.value[i].first + mult * (entry.value[i].first - entry.value[j].first), entry.value[i].second + mult * (entry.value[i].second - entry.value[j].second))
                        if (aNode1.first >= 0 && aNode1.first < input[0].length && aNode1.second >= 0 && aNode1.second < input.size) antiNodeMap.add(aNode1)
                        else mult = -100
                        mult++
                    }
                    mult = 0
                    while (mult >= 0) {
                        val aNode2 = Pair(entry.value[j].first + mult * (entry.value[j].first - entry.value[i].first), entry.value[j].second + mult * (entry.value[j].second - entry.value[i].second))
                        if (aNode2.first >= 0 && aNode2.first < input[0].length && aNode2.second >= 0 && aNode2.second < input.size) antiNodeMap.add(aNode2)
                        else mult = -100
                        mult++
                    }
                }
            }
        }

        return antiNodeMap.size
    }

// check provided examples
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

// actual input
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
