fun main() {
    out(intArrayOf(1, 2, 3, 4))
}

fun out(array: IntArray) {
    val list = mutableListOf<Int>()
    val used = BooleanArray(array.size)
    exec(list, array, used)
}

fun exec(list: MutableList<Int>, array: IntArray, used: BooleanArray) {
    if (list.size == array.size) {
        println(list.toString())
    } else {
        array.forEachIndexed { i, v ->
            if (!used[i]) {
                list += v
                used[i] = true
                exec(list, array, used)
                used[i] = false
                list.removeLast()
            }
        }
    }
}

