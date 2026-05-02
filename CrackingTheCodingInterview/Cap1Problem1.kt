//Determine if a string has all Unique Characters

//Using Data Structure Set Solution
fun isUnique(inputString: String) {
    val elements = inputString.split("").joinToString("").toList()
    val setElements = elements.toSet()
    print("${inputString}: ")
    if (elements.size == setElements.size) {
        println("All the elements are unique")
    } else {
        println("There are repeated elements")
    }
}
//Using no Data Structure
fun isUniqueLoop(inputString: String){
    val charArray = inputString.toCharArray()
    val stringSorted = charArray.sorted()
    println(stringSorted)
    for (index in 0 .. stringSorted.size - 2) {
        if (stringSorted[index] == stringSorted[index+1]) {
            return println("There are repeated elements")
        }
    }
    return println("All the elements are unique")
}


fun main(){
    val string1 = "abcde"
    val string2 = "abvsdba"
    isUnique(string1)
    isUnique(string2)
    isUniqueLoop(string1)
    isUniqueLoop(string2)
}