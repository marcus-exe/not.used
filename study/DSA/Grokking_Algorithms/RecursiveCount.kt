package grokking_Algorithms

fun recursiveCount(list: MutableList<Int>) : Int {
    return if (list.isEmpty()){
        return 0
    } else {
        val temp: Int = 1
        list.removeAt(0)
        temp + recursiveCount(list)
    }
}

fun main(){
    val list: MutableList<Int> = mutableListOf<Int>(1, 2, 3, 4, 5)
    println(recursiveCount(list))

}