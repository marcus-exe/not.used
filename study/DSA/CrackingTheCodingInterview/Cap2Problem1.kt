
fun main(){
    //Normal Kotlin
    val list = listOf(1, 1, 1, 2, 2, 3)
    println("With dups: $list")
    println("Without dups: ${removeDups(list)}")


    //From Scratch Solution
    println("This was created from Scratch")


    val linkedList = LinkedList<Int>()
    for (i in 0..10 ){
        linkedList.append(i)
        if (i % 2 != 0) {
            linkedList.append(i)
        }
    }

    print("Original List: ")
    linkedList.printList()

    //remove duplicates
    linkedList.removeDups()

    print("List after removing duplicates: ")
    linkedList.printList()


}

// Normal kotlin solution
fun removeDups(list: List<Int>) : List<Int>{
    val noDups = list.toSet().toList()
    return noDups
}

