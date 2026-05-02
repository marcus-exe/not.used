package grokking_Algorithms

fun sumRecursive(list: MutableList<Int>): Int {
    return if (list.isEmpty()) {
        0
    } else {
        val temp:Int = list[0]
        list.removeAt(0)
        temp + sumRecursive(list)
    }
}

fun main(){
    val list : MutableList<Int> = mutableListOf<Int>(1, 2, 3, 4, 5, 6)
    println(sumRecursive(list))

}