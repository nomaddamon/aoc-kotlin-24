fun main() {

    fun processRegion(x: Int, y: Int, map: HashMap<Int, HashMap<Int, Char>>, usedMap: HashMap<Int, HashSet<Int>>, region: Region): Region {

        //mark used
        usedMap[x]!!.add(y)

        region.area++

        if (x == 0 || map[x - 1]!![y] != region.char) {
            region.perimeter++
            // count LEFT side of lower-right A only when it "first" from top-left
            // BB
            // BA
            // or
            // AA
            // BA
            if (y == 0 || map[x]!![y - 1] != region.char || x > 0 && map[x]!![y - 1] == region.char && map[x - 1]!![y - 1] == region.char) region.sides++
        } else {
            if (!usedMap[x - 1]!!.contains(y)) processRegion(x - 1, y, map, usedMap, region)
        }

        if (y == 0 || map[x]!![y - 1] != region.char) {
            region.perimeter++
            // check left and top-left for sides
            // count TOP side of lower-right A only when it "first" from top-left
            // BB
            // BA
            // or
            // AB
            // AA
            if (x == 0 || map[x - 1]!![y] != region.char || y > 0 && map[x - 1]!![y] == region.char && map[x - 1]!![y - 1] == region.char) region.sides++
        } else {
            if (!usedMap[x]!!.contains(y - 1)) processRegion(x, y - 1, map, usedMap, region)
        }

        if (x == map.size - 1 || map[x + 1]!![y] != region.char) {
            region.perimeter++
            // count RIGHT side of top-left A only when it "first" from bottom-right
            // AB
            // BB
            // or
            // AB
            // AA
            if (y == map[0]!!.size - 1 || map[x]!![y + 1] != region.char || x < map.size - 1 && map[x]!![y + 1] == region.char && map[x + 1]!![y + 1] == region.char) region.sides++
        } else {
            if (!usedMap[x + 1]!!.contains(y)) processRegion(x + 1, y, map, usedMap, region)
        }

        if (y == map[0]!!.size - 1 || map[x]!![y + 1] != region.char) {
            region.perimeter++
            // count BOTTOM side of top-left A only when it "first" from bottom-right
            // AB
            // BB
            // or
            // AA
            // BA
            if (x == map.size - 1 || map[x + 1]!![y] != region.char || y < map[0]!!.size - 1 && map[x + 1]!![y] == region.char && map[x + 1]!![y + 1] == region.char) region.sides++
        } else {
            if (!usedMap[x]!!.contains(y + 1)) processRegion(x, y + 1, map, usedMap, region)
        }

        return region
    }

    fun parseMapAndCalculateRegions(input: List<String>): List<Region> {
        val hashMap = HashMap<Int, HashMap<Int, Char>>()
        val usedMap = HashMap<Int, HashSet<Int>>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (i == 0) {
                    hashMap[j] = HashMap()
                    usedMap[j] = HashSet()
                }
                hashMap[j]!![i] = input[i][j]  // x-y
            }
        }

        val regions = mutableListOf<Region>()

        for (x in 0 until hashMap.size) {
            for (y in 0 until hashMap[0]!!.size) {
                if (usedMap[x]!!.contains(y)) continue
                regions.add(processRegion(x, y, hashMap, usedMap, Region(x, y, hashMap[x]!![y]!!, 0, 0, 0)))
            }
        }
        return regions
    }

    fun part1(input: List<String>): Int {
        val regions = parseMapAndCalculateRegions(input)
        return regions.sumOf { it.area * it.perimeter }
    }


    fun part2(input: List<String>): Int {
        val regions = parseMapAndCalculateRegions(input)
        return regions.sumOf { it.area * it.sides }
    }

    // check provided examples
    val testInput1 = readInput("Day12_test1")
    val testInput2 = readInput("Day12_test2")
    val testInput3 = readInput("Day12_test3")
    val testInput4 = readInput("Day12_test4")

    check(part1(testInput1) == 140)
    check(part1(testInput2) == 772)
    check(part1(testInput3) == 1930)

    check(part2(testInput1) == 80)
    check(part2(testInput2) == 436)
    check(part2(testInput3) == 1206)
    check(part2(testInput4) == 368)

    // actual input
    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}

data class Region(val x: Int, val y: Int, val char: Char, var area: Int, var perimeter: Int, var sides: Int)