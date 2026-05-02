// Palindrome

fun main() {
    val list1 = mutableListOf(1, 2, 3, 2, 3, 3, 1)
    val list2 = mutableListOf(2, 3, 1, 1, 2, 3, 3)
    if (isAnagram(list1, list2)) {
        println("Is anagram")
    } else {
        println("Isn't anagram")
    }

    //From Scratch Solution
    println("This was created from Scratch")
    val linkedList = LinkedList<Int>()
    linkedList.append(3)
    linkedList.append(3)
    linkedList.append(2)
    linkedList.append(1)
    linkedList.append(2)
    linkedList.append(3)
    linkedList.append(3)

    linkedList.printList()
    if (linkedList.isPalindrome()){
        println("Is palindrome")
    } else {
        println("Is not palindrome")
    }


}

fun isAnagram(list1: MutableList<Int>, list2: MutableList<Int>): Boolean {
    val hashMap1 = hashMapOf<Int, Int>()
    val hashMap2 = hashMapOf<Int, Int>()

    list1.forEach { key ->
        if (hashMap1.containsKey(key)) {
            hashMap1[key] = hashMap1[key]!! + 1
        } else {
            hashMap1[key] = 1
        }
    }
    list2.forEach { key ->
        if (hashMap2.containsKey(key)) {
            hashMap2[key] = hashMap2[key]!! + 1
        } else {
            hashMap2[key] = 1
        }
    }
    return hashMap1 == hashMap2
}