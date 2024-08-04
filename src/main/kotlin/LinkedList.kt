class LinkedNode<E>(val `val`: E) {
    var next: LinkedNode<E>? = null

    var rand: LinkedNode<E>? = null
    fun toContentString(): String {
        var curr: LinkedNode<E>? = this
        val result = mutableListOf<E>()
        while (curr != null) {
            result += curr.`val`
            curr = curr.next
        }
        return result.toString()
    }
}

/**
 * Description: [链表 ]
 * Program:  LinkedList Created on 2024/7/21 20:05
 *
 * @author <a href="mailto: tablojhin.lb@alibaba-inc.com">Tablo</a>
 * @version 1.0
 */
fun main() {
    val list = buildLinkedList(intArrayOf(0, 1, 2, 3, 4))
    val list1 = buildLinkedList1(intArrayOf(0, 1, 2, 3, 4))

    println("buildList1: ${list?.toContentString()}")
    println("buildList2: ${list1?.toContentString()}")

    val reversal = reversal(list)
    println("reversal: ${reversal?.toContentString()}")

    val reversal2 = reversal2(reversal)
    println("reversal2: ${reversal2?.toContentString()}")

    val reversal3 = reversal3(reversal2)
    println("reversal3: ${reversal3?.toContentString()}")

    val reversalRange = reversalRange(reversal3, 2, 4)
    println("reversalRange: ${reversalRange?.toContentString()}")

    val midOrUp = midOrUp(reversalRange)
    println("midOrUp: ${midOrUp?.`val`}")

    val even = buildLinkedList(intArrayOf(1, 2, 3, 4))

    val midOrUpEven = midOrUp(even)
    println("midOrUpEven: ${midOrUpEven?.`val`}")

    val midOrDown = midOrDown(reversalRange)
    println("midOrDown: ${midOrDown?.`val`}")

    val midOrDownEven = midOrDown(even)
    println("midOrDownEven: ${midOrDownEven?.`val`}")

    val midOrUpMidPre = midOrUpMidPre(reversalRange)
    println("midOrUpMidPre: ${midOrUpMidPre?.`val`}")

    val midOrUpMidPreEven = midOrUpMidPre(even)
    println("midOrUpMidPreEven: ${midOrUpMidPreEven?.`val`}")

    val midOrDownMidPre = midOrDownMidPre(reversalRange)
    println("midOrDownMidPre: ${midOrDownMidPre?.`val`}")

    val midOrDownMidPreEven = midOrDownMidPre(even)
    println("midOrDownMidPreEven: ${midOrDownMidPreEven?.`val`}")

    val palindromeEven = buildLinkedList(intArrayOf(1, 2, 2, 1))
    println("palindromeEven: ${palindrome(palindromeEven)}")
    println("palindromeEvenNode: ${palindromeEven?.toContentString()}")


    val palindrome = buildLinkedList(intArrayOf(1, 2, 3, 2, 1))
    println("palindrome: ${palindrome(palindrome)}")
    println("palindromeNode: ${palindrome?.toContentString()}")

    val noPalindrome = buildLinkedList(intArrayOf(1, 2, 3, 5, 1))
    println("noPalindrome: ${palindrome(noPalindrome)}")
    println("noPalindromeNode: ${noPalindrome?.toContentString()}")

    val partition = partition(buildLinkedList(intArrayOf(1, 2, 2, 1, 3, 5)), 2)
    println("partition: ${partition?.toContentString()}")
    val partition2 = partition(buildLinkedList(intArrayOf(1, 2, 3, 2, 1, 7, 3)), 2)
    println("partition2: ${partition2?.toContentString()}")

    val partition3 = partition2(buildLinkedList(intArrayOf(1, 2, 2, 1, 3, 5)), 2)
    println("partition3: ${partition3?.toContentString()}")
    val partition4 = partition2(buildLinkedList(intArrayOf(1, 2, 3, 2, 1, 7, 3)), 2)
    println("partition4: ${partition4?.toContentString()}")

    val noLoop = loop(buildLinkedList(intArrayOf(1, 2, 3, 4, 5)))
    println("noLoop: ${noLoop?.`val`}")
    val loopNode = LinkedNode(1)
    val loop2 = LinkedNode(2)
    val loop3 = LinkedNode(3)
    val loop4 = LinkedNode(3)
    loopNode.next = loop2
    loop2.next = loop3
    loop3.next = loop4
    loop4.next = loop2
    val loop = loop(loopNode)
    println("loop: ${loop?.`val`}")


    val noLoop2 = loop2(buildLinkedList(intArrayOf(1, 2, 3, 4, 5)))
    println("noLoop2: ${noLoop2?.`val`}")

    val loopNode2 = loop2(loopNode)
    println("loop2: ${loopNode2?.`val`}")

}

fun buildLinkedList(arr: IntArray): LinkedNode<Int>? {
    if (arr.isEmpty()) return null
    val vNode = LinkedNode(-1)
    var curr = vNode
    arr.forEach {
        val node = LinkedNode(it)
        curr.next = node
        curr = node
    }
    return vNode.next
}

fun buildLinkedList1(arr: IntArray): LinkedNode<Int>? {
    if (arr.isEmpty()) return null
    val head = LinkedNode(arr.first())
    var curr = head
    for (i in 1 until arr.size) {
        val node = LinkedNode(arr[i])
        curr.next = node
        curr = node
    }
    return head
}
fun reversal(head: LinkedNode<Int>?): LinkedNode<Int>? {
    head?.next?.run {
        var pre: LinkedNode<Int>? = null
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

fun reversal2(head: LinkedNode<Int>?): LinkedNode<Int>? {
    head?.next?.let {
        val vNode = LinkedNode(-1)
        vNode.next = head
        while (head.next != null) {
            val next = head.next
            head.next = next!!.next
            next.next = vNode.next
            vNode.next = next
        }
        return vNode.next
    }
    return head
}

fun reversal3(head: LinkedNode<Int>?): LinkedNode<Int>? {
    head?.next?.let {
        val last = reversal3(it)
        it.next = head
        head.next = null
        return last
    }
    return head
}

fun reversalRange(head: LinkedNode<Int>?, l: Int, r: Int): LinkedNode<Int>? {
    if(l >= r) return head
    head?.next?.let {
        val vNode = LinkedNode(-1)
        var edge = vNode
        edge.next = head
        for (i in 1 until l) {
            edge = edge.next!!
        }
        val curr = edge.next!!
        for (i in l until r) {
            val next = curr.next
            curr.next = next!!.next
            next.next = edge.next
            edge.next = next
        }
        return vNode.next
    }
    return head
}


fun midOrUp(head: LinkedNode<Int>?): LinkedNode<Int>? {
    head?.next?.next?.let {
        var slow = head.next
        var fast = it
        while (fast?.next?.next != null) {
            slow = slow!!.next
            fast = fast.next!!.next!!
        }
        return slow
    }
    return head
}

fun midOrDown(head: LinkedNode<Int>?): LinkedNode<Int>? {
    head?.next?.let {
        var slow = it
        var fast = it
        while (fast.next?.next != null) {
            slow = slow.next!!
            fast = fast.next!!.next!!
        }
        return slow
    }
    return head
}

fun midOrUpMidPre(head: LinkedNode<Int>?): LinkedNode<Int>? {
    head?.next?.next?.let {
        var slow = head
        var fast = it
        while (fast.next?.next != null) {
            slow = slow!!.next
            fast = fast.next!!.next!!
        }
        return slow
    }
    return head
}

fun midOrDownMidPre(head: LinkedNode<Int>?): LinkedNode<Int>? {
    head?.next?.next?.let {
        var slow = head
        var fast: LinkedNode<Int>? = head.next
        while (fast?.next?.next != null) {
            slow = slow!!.next
            fast = fast.next!!.next
        }
        return slow
    }
    return head?.next
}


fun palindrome(head: LinkedNode<Int>?): Boolean {
    head?.next?.let {
        val lastHead = midOrUpMidPre(head)
        val reversal = reversal(lastHead!!.next)
        var result = true
        var curr = head
        var lastCurr = reversal
        while (curr != reversal && curr != null && lastCurr != null) {
            if (curr.`val` != lastCurr.`val`) {
                result = false
                break
            }
            curr = curr.next
            lastCurr = lastCurr.next
        }
        val regression = reversal(reversal)
        lastHead.next = regression
        return result
    }
    return true
}

fun partition(head: LinkedNode<Int>?, num: Int): LinkedNode<Int>? {
    head?.let {
        val list = mutableListOf(it)
        var curr = head.next
        while (curr != null) {
            list += curr
            curr = curr.next
        }

        var i = 0
        var less = -1
        var more = list.size
        while (i < more) {
            if (list[i].`val` < num) {
                swap(list, i++, ++less)
            } else if (list[i].`val` == num) {
                i++
            } else {
                swap(list, i, --more)
            }
        }
        val vNode = LinkedNode(-1)
        vNode.next = list.first()
        curr = vNode
        list.forEach {node ->
            node.next = null
            curr!!.next = node
            curr = node
        }
        return vNode.next
    }
    return null
}

fun partition2(head: LinkedNode<Int>?, num: Int): LinkedNode<Int>? {
    head?.let {
        var lessHead: LinkedNode<Int>? = null
        var lessTail: LinkedNode<Int>? = null
        var eqHead: LinkedNode<Int>? = null
        var eqTail: LinkedNode<Int>? = null
        var moreHead: LinkedNode<Int>? = null
        var moreTail: LinkedNode<Int>? = null
        var curr = head
        while (curr != null) {
            val next = curr.next
            curr.next = null
            if (curr.`val` < num) {
                if (lessHead == null) {
                    lessHead = curr
                    lessTail = curr
                } else {
                    lessTail!!.next = curr
                    lessTail = lessTail.next
                }
            } else if (curr.`val` == num) {
                if (eqHead == null) {
                    eqHead = curr
                    eqTail = curr
                } else {
                    eqTail!!.next = curr
                    eqTail = eqTail.next
                }
            } else {
                if (moreHead == null) {
                    moreHead = curr
                    moreTail = curr
                } else {
                    moreTail!!.next = curr
                    moreTail = moreTail.next
                }
            }
            curr = next
        }
        val follow: LinkedNode<Int>?
        when (eqHead) {
            null -> follow = moreHead
            else -> {
                eqTail!!.next = moreHead
                follow = eqHead
            }
        }
        lessHead?.run {
            lessTail!!.next = follow
            return this
        }
        return follow

    }
    return null
}

fun swap(array: MutableList<LinkedNode<Int>>, source: Int, target: Int) {
    if (source == target) return
    val tmp = array[target]
    array[target] = array[source]
    array[source] = tmp
}

fun loop(head: LinkedNode<Int>?): LinkedNode<Int>? {
    head?.next?.let {
        val set = hashSetOf(head)
        var curr: LinkedNode<Int>? = it
        while (curr != null) {
            set += (if (set.contains(curr)) return curr else curr)
            curr = curr.next
        }
        return null
    }
    return null
}

fun loop2(head: LinkedNode<Int>?): LinkedNode<Int>? {
    head?.next?.let {
        var slow = head.next
        var fast = it.next
        while (fast != slow) {
            if (slow?.next == null || fast?.next?.next == null) {
                return null
            }
            slow = slow.next
            fast = fast.next!!.next
        }
        fast = head
        while (fast != slow) {
            fast = fast!!.next
            slow = slow!!.next
        }
        return slow
    }
    return null
}

fun copyList(head: LinkedNode<Int>?): LinkedNode<Int>? {
    head?.let {
        var curr = head
        val map = hashMapOf<LinkedNode<Int>, LinkedNode<Int>>()
        while (curr != null) {
            map[curr] = LinkedNode(curr.`val`)
            curr = curr.next
        }
        curr = head
        while (curr != null) {
            map[curr]!!.rand = map[curr.rand]
            curr = curr.next
        }
        return map[head]
    }
    return null
}