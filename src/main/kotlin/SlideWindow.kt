fun main() {
    println(lengthOfLongestSubString("abcabcbb"))
    println(lengthOfLongestSubString1("abcabcbb"))
    println(minWindows("ADOBECODEBANC", "ABC"))
    val first = System.currentTimeMillis()
    println(canCompleteCircuit(intArrayOf(1, 2, 3, 4, 5), intArrayOf(3, 4, 5, 2, 1)))
    val second = System.currentTimeMillis()
    println("canCompleteCircuit: ${second - first}ms")
    println(canCompleteCircuit1(intArrayOf(1, 2, 3, 4, 5), intArrayOf(3, 4, 5, 2, 1)))
    val third = System.currentTimeMillis()
    println("canCompleteCircuit1: ${third - second}ms")

    println("balanceString1${balanceString1("WQWRQQQW")}")
    println("balanceString${balanceString("WQWRQQQW")}")


}

fun minSubArrayLen(arr: IntArray, target: Int): Int {
    var result = Int.MAX_VALUE
    var sum = 0
    var l = 0
    arr.forEachIndexed { r, v ->
        sum += v
        while (sum - arr[l] >= target) sum -= arr[l++]
        if (sum >= target) result = minOf(r - l + 1, result)
    }
    return if (result == Int.MAX_VALUE) 0 else result
}

fun lengthOfLongestSubString(s: String): Int {
    val map = hashMapOf<Char, Int>()
    var l = 0
    var result = 0
    s.forEachIndexed { r, c ->
        l = maxOf(l, (map[c] ?: 0) + 1)
        result = maxOf(result, r - l + 1)
        map[c] = r
    }
    return result
}

fun lengthOfLongestSubString1(s: String): Int {
    val map = IntArray(256) {-1}
    var result = 0
    var l = 0
    s.forEachIndexed { r, c ->
        l= maxOf(l, map[c.code]+1)
        result = maxOf(result, r - l + 1)
        map[c.code] = r
    }
    return result
}

fun minWindows(s: String, target: String): String {
    if (s.length < target.length) return ""
    var start = 0
    var len = Int.MAX_VALUE
    var l = 0
    val debt = IntArray(256)
    target.forEach { debt[it.code]-- }
    var need = target.length
    s.forEachIndexed { r, c ->
        if (debt[c.code]++ < 0) need--
        if (need == 0) {
            while (debt[s[l].code] > 0) debt[s[l++].code]--
            if (r - l + 1 < len) {
                start = l
                len = r - l + 1
            }
        }
    }
    return if (len == Int.MAX_VALUE)  "" else s.substring(start, start + len)
}

fun canCompleteCircuit(gas: IntArray, cost: IntArray): Int {
    var sum = 0
    val size = gas.size
    gas.forEachIndexed { l, v ->
        var len = 0
        var r = l
        while (sum >= 0) {
            if (len == size) return l
            r = (l + len++) % size
            sum += gas[r] - cost[r]
        }
        sum -= v - cost[l]
    }
    return -1
}

fun canCompleteCircuit1(gas: IntArray, cost: IntArray): Int {
    var l = 0
    val size = gas.size
    while (l < size) {
        var len = 0
        var r = l
        var sum = 0
        while (sum >= 0) {
            if (len == size) return l
            r = (l + len++) % size
            sum += gas[r] - cost[r]
        }
        l = if (l + len > size) size else ++r
    }
    return -1
}

fun balanceString1(s: String): Int {
    val size = s.length
    val require = size / 4
    val count = IntArray(4)
    val arr = s.map {
        val i = mapToInt(it)
        count[i]++
        i
    }
    var r = 0
    var result = size
    arr.forEachIndexed { l, v ->
        while ((!(ok(count, r - l, require)) && r < size)) count[arr[r++]]--
        if (ok(count, r - l, require)) result = minOf(result, r - l)
        count[v]++
    }
    return result
}

fun balanceString(string: String): Int {
    val size = string.length
    val require = size / 4
    var l = 0
    val count = IntArray(4)
    var result = size
    val list = string.map {
        val i = mapToInt(it)
        count[i]++
        i
    }
    list.forEachIndexed { r, v ->
        count[v]--
        if (ok(count, r - l + 1, require)) {
            do count[list[l++]]++
            while (ok(count, r - l + 1, require) && l <= r)
            if (!ok(count, r - l + 1, require)) count[list[--l]]--
            result = minOf(result, r - l + 1)
        }
    }
    return result
}

fun ok(count: IntArray, len: Int, require: Int): Boolean {
    var length = len
    for (i in 0 until 4) {
        if (count[i] > require) return false
        length -= require - count[i]
    }
    return length == 0
}

private fun mapToInt(it: Char) = if (it == 'Q') 0 else if (it == 'W') 1 else if (it == 'E') 2 else 3


fun subArraysWithDistinct(arr: IntArray, k: Int): Int {
    return collect(arr, k) - collect(arr, k - 1)
}

fun collect(arr: IntArray, k: Int): Int {
    var collected = 0
    val count = IntArray(arr.size)
    var l = 0
    var result = 0
    arr.forEachIndexed { r, v ->
        if (count[v]++ == 0) collected++
        if (collected >= k) {
            while (--count[arr[l++]] == 0) collected--
            result += r - l + 1
        }
    }
    return result
}
