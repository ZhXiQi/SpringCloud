// 原地建堆
function buildHeap(items, heapSize) {
    while(heapSize < items.length - 1) {
        heapSize ++
        heapify(items, heapSize)
    }
}

function heapify(items, i) {
    // 自下而上式堆化
		// 节点与父节点比较，不断向上
    while (Math.floor(i/2) > 0 && items[i] < items[Math.floor(i/2)]) {
        swap(items, i, Math.floor(i/2)); // 交换
        i = Math.floor(i/2);
    }
}

function swap(items, i, j) {
    let temp = items[i]
    items[i] = items[j]
    items[j] = temp
}


var advantageCount = function(A, B) {
    A.sort((a, b) => a - b)
    let res = []
    let result = 0;
    for(let b of B){
        let i = 0
        while(i < A.length && A[i] <= b) i++   // 找 大于b的 最小的牌
        if(i < A.length){                      // 找到了
            res.push(A.splice(i, 1)[0])
        } else {                               // 找不到
            res.push(A.shift())
            result = result + 1;
        }
    }
    return result
}

// 测试
var items = [,5, 2, 3, 4, 1]
// 初始有效序列长度为 1
buildHeap(items, 1)
console.log(items)
// [empty, 1, 2, 3, 5, 4]
var A = [1,3,1,4]
var B = [1,2,3,4]
let re = advantageCount(A,B)
console.log(re)


var a = {
    i : 1,
    valueOf: function () {
        if (this.i === 1) {
            this.i++;
            return 1;
        } else {
            return 12;
        }
    }
}
if (a == 1 && a == 12) {
    console.log("----:"+a)
}