fun main(){

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


    linkedList.removeNode(20)
    linkedList.printList()


}

