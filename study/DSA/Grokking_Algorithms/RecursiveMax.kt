package grokking_Algorithms

fun recursiveMax(list: MutableList<Int>): Int {

    return if (list.size > 2) {
        if (list[0] > list[1]) list[1] = list[0]
        list.removeAt(0)
        recursiveMax(list)
    } else {
        if (list[0] > list[1]) list[0]
        else list[1]
    }
}

fun main(){
    val list:MutableList<Int> = mutableListOf(1, 2, 3, 4, 5, 4, 3, 2, 1)
    println(recursiveMax(list))

}