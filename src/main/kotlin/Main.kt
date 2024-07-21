fun main() {

}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

fun reversal(head: ListNode?): ListNode? {
    head?.next?.let {
        var pre: ListNode? = null
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

fun reversal2(head: ListNode?): ListNode? {
    head?.next?.let {
        val vNode = ListNode(1)
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