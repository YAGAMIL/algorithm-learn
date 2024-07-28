import java.util.LinkedList
import java.util.Queue
import java.util.Stack
import kotlin.random.Random

fun main() {
    println(longestPalindrome("cbbd"))
    println(2 / 4)
    println(convert("ABCDE", 4))
    val arr = intArrayOf(3, 2, 1, 4)
    quickSort(arr)
    println(arr.contentToString())
    val mergeArr = intArrayOf(3, 2, 1, 4, -2)
    mergeIt(mergeArr)
//    mergeSort(mergeArr)

    println(mergeArr.contentToString())

    val node = buildTree(arrayListOf(1, null, 2, 3))
    println(inorder(node))

    println(longestNonRepeatingSubstring("abcabcbb"))

}

fun quickSort(array: IntArray) {
    if (array.isEmpty()) return
    quickSort(array, 0, array.lastIndex)
}

fun quickSort(array: IntArray, l: Int, r: Int) {
    if (l >= r) return
    val region = partition(array, l, r)
    quickSort(array, l, region.first)
    quickSort(array, region.second, r)
}

fun partition(array: IntArray, l: Int, r: Int): Pair<Int, Int> {
    if (l >= r) return Pair(-1, -1)
    val num = array[Random.nextInt(l, r)]
    var less = l - 1
    var more = r + 1
    var i = l
    while (i < more) {
        if (array[i] < num) {
            swap(array, i++, ++less)
        } else if (array[i] == num) {
            i++
        } else {
            swap(array, i, --more)
        }
    }
    return Pair(less, more)
}

fun swap(array: IntArray, source: Int, target: Int) {
    if (source == target) return
    array[source] = array[source] xor array[target]
    array[target] = array[source] xor array[target]
    array[source] = array[source] xor array[target]
}

fun mergeIt(array: IntArray) {
    if (array.isEmpty()) return
    var mergeSize = 1
    val edgeSize = array.size shr 1
    while (mergeSize < array.size) {
        var i = 0
        while (i < array.size) {
            val mid = i + mergeSize - 1
            if (mid >= array.lastIndex) {
                break
            }
            val right = minOf(mid + mergeSize, array.lastIndex)
            merge(array, i, mid, right)
            i = right + 1
        }
        if (mergeSize > edgeSize) break
        mergeSize = mergeSize shl 1
    }
}

fun mergeSort(arr: IntArray) {
    if (arr.isEmpty()) return
    mergeSort(arr, 0, arr.lastIndex)
}

fun mergeSort(arr: IntArray, l: Int, r: Int) {
    if (l >= r) return
    val mid = l + (r - l shr 1)
    mergeSort(arr, l, mid)
    mergeSort(arr, mid + 1, r)
    merge(arr, l, mid, r)
}

fun merge(arr: IntArray, l: Int, mid: Int, r: Int) {
    if (l >= r) return
    val help = IntArray(r - l + 1)
    var i = 0
    var left = l
    var right = mid + 1
    while (left <= mid && right <= r) {
        help[i++] = if (arr[left] < arr[right]) arr[left++] else arr[right++]
    }
    while (left <= mid) {
        help[i++] = arr[left++]
    }
    while (right <= r) {
        help[i++] = arr[right++]
    }
    help.indices.forEach { arr[it + l] = help[it] }
}

fun longestPalindrome(s: String?): String? {
    if (s == null || s.length < 2) return s
    var result = String()
    s.indices.forEach {
        val palindrome1: String = palindrome(s, it, it)
        val palindrome2: String = palindrome(s, it, it + 1)
        val palindrome = if (palindrome1.length > palindrome2.length) palindrome1 else palindrome2
        result = if (palindrome.length > result.length) palindrome else result
    }
    return result
}

private fun palindrome(s: String, l: Int, r: Int): String {
    var left = l
    var right = r
    while (left >= 0 && right < s.length && s[left] == s[right]) {
        left--
        right++
    }
    return s.substring(left + 1, right)
}

fun convert(s: String, numRows: Int): String {
    if (s.length < numRows || numRows < 2) return s
    val cycle = (numRows shl 1) - 2
    val row = ((s.length / cycle) + 1) * (1 + numRows - 2)
    val lines = Array(numRows) { arrayOfNulls<Char>(row) }
    var y = 0
    s.indices.forEach {
        // 除以周期，单独周期的第几个数字
        val i = it % cycle
        val line: Int
        if (i < numRows) {
            line = i
        } else {
            y++
            line = cycle - i
        }
        lines[line][y] = s[it]
        if ((it + 1) % cycle == 0) y++

    }
    return lines.asSequence().flatMap { it.asSequence() }.filter { it != null }.joinToString(separator = "")
}

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

enum class OrderType {
    PRE, IN, POST
}

fun order(root: TreeNode?, type: OrderType, result: MutableList<Int>) {
    root?.let {
        if (type == OrderType.PRE) result += root.`val`
        order(root.left, type, result)
        if (type == OrderType.IN) result += root.`val`
        order(root.right, type, result)
        if (type == OrderType.POST) result += root.`val`
    }
}

fun preorder(root: TreeNode?): List<Int> {
    val result = mutableListOf<Int>()
    root?.let {
        var curr = it
        val stack = Stack<TreeNode>()
        stack.push(curr)
        while (stack.isNotEmpty()) {
            curr = stack.pop()
            result += curr.`val`
            curr.right?.let(stack::push)
            curr.left?.let(stack::push)
        }
    }
    return result
}

fun postorder(root: TreeNode?): List<Int> {
    val result = mutableListOf<Int>()
    root?.let {
        val stack1: Stack<TreeNode> = Stack()
        val stack: Stack<TreeNode> = Stack()
        stack1.push(it)
        while (stack1.isNotEmpty()) {
            val curr = stack1.pop()
            stack1.push(curr)
            curr.left?.let(stack1::push)
            curr.right?.let(stack1::push)
        }
        while (stack.isNotEmpty()) {
            result += stack1.pop().`val`
        }
    }
    return result
}

fun inorder(root: TreeNode?): List<Int> {
    val result = mutableListOf<Int>()
    root?.let {
        val stack = Stack<TreeNode>()
        var curr = root
        while (stack.isNotEmpty() || curr != null) {
            if (curr != null) {
                stack.push(curr)
                curr = curr.left
            } else {
                curr = stack.pop()
                result += curr.`val`
                curr = curr.right
            }
        }
    }
    return result
}

fun levelOrderQueue(root: TreeNode?): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    root?.let {
        val queue = LinkedList<TreeNode>()
        queue.offer(it)
        var size = queue.size
        while (size > 0) {
            val tmp = mutableListOf<Int>()
            for (i in 0 until size) {
                val curr = queue.pop()
                tmp += curr.`val`
                curr.left?.let(queue::offer)
                curr.right?.let(queue::offer)
            }
            size = queue.size
            result += tmp
        }
    }
    return result
}

fun levelOrderList(root: TreeNode?): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    root?.let {
        var levelList = mutableListOf(it)
        while (levelList.isNotEmpty()) {
            result += levelList.map(TreeNode::`val`)
            val nextLevel = mutableListOf<TreeNode>()
            levelList.forEach { node ->
                node.left?.let(nextLevel::add)
                node.right?.let(nextLevel::add)
            }
            levelList = nextLevel
        }
    }
    return result
}

fun width(root: TreeNode?): Int {
    var max = 0
    root?.let {
        var levelList = mutableListOf(Pair(it, 1))
        while (levelList.isNotEmpty()) {
            val nextLevel = mutableListOf<Pair<TreeNode, Int>>()
            levelList.forEach { pair ->
                pair.first.left?.let { node -> nextLevel.addFirst(Pair(node, pair.second shl 1)) }
                pair.first.right?.let { node -> nextLevel += Pair(node, (pair.second shl 1) + 1) }
            }
            max = maxOf(max, nextLevel.last().second - nextLevel.first().second + 1)
            levelList = nextLevel
        }
    }
    return max
}

fun buildTree(arr: List<Int?>): TreeNode? {
    var head: TreeNode? = null
    arr.first()?.let {
        val queue: Queue<TreeNode> = LinkedList()
        head = TreeNode(it)
        queue.offer(head)
        var i = 1
        while (i < arr.size && queue.isNotEmpty()) {
            val curr = queue.poll()
            arr[i++]?.run { curr.left = buildNodeAndOffer(queue) }
            if (i + 1 < arr.size) arr[i++]?.run { curr.right = buildNodeAndOffer(queue) }
        }
    }
    return head
}

private fun Int.buildNodeAndOffer(queue: Queue<TreeNode>): TreeNode {
    val node = TreeNode(this)
    queue.offer(node)
    return node
}

fun longestNonRepeatingSubstring(s: String): String {
    if (s.isBlank()) return s
    var result = String()
    s.indices.forEach { i ->
        val set = hashSetOf(s[i])
        var l = i - 1
        var r = i + 1
        while (l >= 0 && !set.contains(s[l])) set.add(s[l--])
        while (r < s.length && !set.contains(s[r])) set.add(s[r++])
        val curStr = s.substring(l + 1, r)
        result = if (result.length >= curStr.length) result else curStr
    }
    return result
}

fun lengthOfLongestSubstring(s: String): Int {
    if (s.isEmpty()) return 0
    var max = 0
    val set = HashSet<Char>()
    s.indices.forEach {
        if (it > 0) set.remove(s[it - 1])
        if (!set.contains(s[it])) set.add(s[it])
        var r = it + 1
        while (r < s.length && !set.contains(s[r])) set.add(s[r++])
        max = maxOf(max, r - it, s.length - it - 1)
    }
    return max
}

class Node(var value: Int) {
    var next: Node? = null
}

fun reversal(head: Node?): Node? {
    head?.next?.let {
        var pre:Node? = null
        var curr = head
        while (curr != null) {
            val next = curr.next
            curr.next = pre
            pre = curr
            curr = next
        }
        return pre
    }
    return head
}