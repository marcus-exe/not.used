//Return Kth to Lat
fun main(){
    //Normal Kotlin
    val list = listOf<Int>(1, 2, 3, 4, 5, 6)
    println("The last number is ${returnLast(list)}")
    println()

    //From Scratch Solution
    println("This was created from Scratch")


    val linkedList = LinkedList<Int>()
    for (i in 0..10 ){
        linkedList.append(i)
        if (i % 2 != 0) {
            linkedList.append(i)
        }
    }
    linkedList.append(19)
    linkedList.append(20)

    linkedList.printList()
    val last = linkedList.returnKthToLast(1)
    println(last)

}
// find last using normal kotlin
fun returnLast(list: List<Int>): Int{
    return list.last()
}

