fun main(){
    //From Scratch Solution
    println("This was created from Scratch")
    val linkedList = LinkedList<Int>()

    linkedList.append(3)
    linkedList.append(5)
    linkedList.append(8)
    linkedList.append(5)
    linkedList.append(10)
    linkedList.append(2)
    linkedList.append(1)


    linkedList.printList()
    linkedList.partition(5)
    linkedList.printList()

}