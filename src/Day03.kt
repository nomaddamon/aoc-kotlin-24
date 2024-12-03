fun main() {
    val regex = Regex("mul\\(([0-9]+),([0-9]+)\\)")

    fun part1(input: String): Int {
        return regex.findAll(input).sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
    }

    fun part2(input: String): Int {
        // pad with first do() to enable calculations, split by don't()
        val parts = ("do()$input").split("don't()").filter { it.isNotEmpty() }
        return parts.sumOf { part ->
            // if at least one do() is found, use input after it with same algorithm as part1
            val doParts = part.split("do()", limit = 2)
            if (doParts.size > 1) {
                part1(doParts[1])
            } else {
                0
            }
        }
    }

    // simple tests
    check(part1("mul(4*mul(333,1)mul(6,9!mul(2, 2)?(mul(832,717)12,34)mul(2,2") == 596877)
    check(part2("mul(4*mul(333,1)don't()mul(6,9!mul(2, 2)?(mul(832,717)12,34)do()ddmul(2,2)") == 337)

    // check provided examples
    check(part1("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))") == 161)
    check(part2("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))") == 48)

    // actual input
    val input = readInputText("Day03")
    part1(input).println()
    part2(input).println()
}
