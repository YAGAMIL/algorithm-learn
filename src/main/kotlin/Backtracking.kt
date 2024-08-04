fun main() {
    out(intArrayOf(1, 2, 3, 4))
}

fun out(array: IntArray) {
    val list = mutableListOf<Int>()
    val used = BooleanArray(array.size) { false }
    exec(list, array, used)
}

fun exec(list: MutableList<Int>, array: IntArray, used: BooleanArray) {
    if (list.size == array.size) {
        println(list.toString())
    } else {
        array.forEachIndexed(){i, v ->
            if (!used[i]) {
                used[i] = true
                list += v
                exec(list, array, used)
                list -= v
                used[i] = false
            }
        }
    }
}

