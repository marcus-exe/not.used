package grokking_Algorithms

fun searchSmallerIndex(array: ArrayList<Int>) : Int{
    var smallerNumber = array[0]
    var smallerIndex: Int = 0

    for (i in 0..<array.size) {
        if (array[i] < smallerNumber){
            smallerNumber = array[i]
            smallerIndex = i
        }
    }
    return smallerIndex
}


fun selectionSort(array: ArrayList<Int>) : MutableList<Int>{
    val newList : MutableList<Int> = mutableListOf<Int>()
    for (i in 0..<array.size) {
        val smallerIndex = searchSmallerIndex(array)
        newList.add(array[smallerIndex])
        array.removeAt(smallerIndex)
    }
    return newList
}

fun main(){
    val array = arrayListOf<Int>(0, 2, 5, 1, 8, 23, 31, 21, 93, 213, 31, 11, 1512, 231, 341, 516, 132, 322, 421, 643)
    println("Original Array Before: $array")
    println("Sorted List After: ${selectionSort(array)}")
    println("Original Array After: $array")

}