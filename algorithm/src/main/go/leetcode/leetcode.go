package leetcode

import (
	"fmt"
	"math"
)

type ListNode struct {
  Val int
  Next *ListNode
}

/**
	合并k个升序链表
 */
func mergeKLists(lists []*ListNode) *ListNode {
	head := &ListNode{Next: nil,Val: math.MinInt32}
	for i := 0; i < len(lists); i++ {
		head = mergeList(head,lists[i])
	}
	return head.Next
}

func mergeList(l1 *ListNode, l2 *ListNode) *ListNode {
	if l1 == nil {
		return l2
	}
	if l2 == nil {
		return l1
	}
	if l1.Val >= l2.Val {
		l2.Next = mergeList(l1,l2.Next)
		return l2
	} else {
		l1.Next = mergeList(l1.Next,l2)
		return l1
	}
}

func LengthOfLIS(nums []int) int {
	if len(nums) == 0 {
		return 0
	}
	len := len(nums)
	result := 1

	var dp []int = make([]int,len)

	for i := 0; i< len; i++ {
		dp[i] = 1
		for j := 0; j < i; j++ {
			if nums[j] > nums[i] {
				dp[i] = int(math.Max(float64(dp[i]), float64(dp[j]+1)))
			}
			fmt.Println(dp[i])
			result = int(math.Max(float64(result), float64(dp[i])))
			fmt.Println(result)
		}
	}
	return result
}
