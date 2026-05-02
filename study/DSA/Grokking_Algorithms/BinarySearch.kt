package grokking_Algorithms

fun binarySearch(item: Int, list : List<Int>) : Int? {
    var min = 0
    var max = list.lastIndex

    while (min <= max) {
        val mid = (min + max)/2
        val guess = list[mid]
        if (guess == item) return mid
        if (guess > item) max = mid -1
        else min = mid + 1
    }
    return null

}

val sortedArray = (0..100 step 3).toList()

fun main(){
    println(sortedArray)
    print(binarySearch(3, sortedArray))
}