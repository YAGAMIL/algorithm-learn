/**
 * Description: 滑动窗口
 * Program:  SlideWindow Created on 2024/8/1 22:48
 *
 * @author <a href="mailto: tablojhin.lb@alibaba-inc.com">Tablo</a>
 * @version 1.0
 */
class SlideWindow {

}

fun main() {
    println(lengthOfLongestSubString("abcabcbb"))
    println(lengthOfLongestSubString1("abcabcbb"))
    println(minWindows("ABCD", "AC"))
    val first = System.currentTimeMillis()
    println(canCompleteCircuit(intArrayOf(1, 2, 3, 4, 5), intArrayOf(3, 4, 5, 2, 1)))
    val second = System.currentTimeMillis()
    println("canCompleteCircuit: ${second - first}ms")
    println(canCompleteCircuit1(intArrayOf(1, 2, 3, 4, 5), intArrayOf(3, 4, 5, 2, 1)))
    val third = System.currentTimeMillis()
    println("canCompleteCircuit1: ${third - second}ms")

}

fun minSubArrayLen(arr: IntArray, target: Int): Int {
    var result = Int.MAX_VALUE
    var l = 0
    var sum = 0
    arr.forEachIndexed { i, v ->
        sum += v
        while (sum - arr[l] >= target) sum -= arr[l++]
        if (sum >= target) result = minOf(result, i - l + 1)
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
    val map = IntArray(256) { -1 }
    var result = 0
    var l = 0
    s.forEachIndexed { i, c ->
        l = maxOf(l, map[c.code] + 1)
        result = maxOf(result, i - l + 1)
        map[c.code] = i
    }
    return result
}

fun minWindows(s: String, target: String): String {
    if (s.length < target.length) return ""
    val dept = IntArray(257)
    target.forEach { dept[it.code]-- }
    var need = target.length
    var start = 0
    var len = Int.MAX_VALUE
    var l = 0
    s.forEachIndexed { r, c ->
        if (dept[c.code]++ < 0) need--
        if (need == 0) {
            while (dept[s[l].code] > 0) dept[s[l++].code]--
            if (r - l + 1 < len) {
                start = l
                len = r - l + 1
            }
        }
    }
    return if (len == Int.MAX_VALUE) "" else s.substring(start, start + len)
}

fun canCompleteCircuit(gas: IntArray, cost: IntArray): Int {
    var sum = 0
    var r = 0
    var len = 0
    val size = cost.size
    gas.forEachIndexed { l, v ->
        while (sum >= 0) {
            if (len == size) return l
            r = (l + len++) % size
            sum += gas[r] - cost[r]
        }
        sum -= v - cost[l]
        len--
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

fun balanceString(string: String): Int {
    var r = 0
    val size = string.length
    val arr = IntArray(size)
    val count = IntArray(4)
    val require = size / 4
    var len = size
    string.forEachIndexed { index, c ->
        val i = if (c == 'Q') 0 else if (c == 'W') 1 else if (c == 'E') 2 else 3
        arr[index] = i
        count[i]++
    }
    arr.forEachIndexed { l, v ->
        while (!ok(r - l, count, require) && r < size) count[arr[r++]]--
        if (ok(r - l, count, require)) len = minOf(r - l, len)
        count[arr[l]]++
    }
    return len
}

fun ok(len: Int, count: IntArray, require: Int): Boolean {
    var length = len
    for (i in 0 until 4) {
        if (require < count[i]) return false
        length -= require - count[i]
    }
    return length == 0
}

