import com.sun.jdi.Value

// Given 2 strings, write a method to decide if one is a permutation of the other

//Make 2 CharArrays, Sort them and Compare
fun isPermutationBF(string1: String, string2: String){
    if (string1.length != string2.length){
        return println("Não é anagrama")
    }
    val charArray1 = string1.toCharArray()
    val sortedArray1 = charArray1.sorted()

    val charArray2 = string2.toCharArray()
    val sortedArray2 = charArray2.sorted()

    if (sortedArray1 == sortedArray2){
        return println("É anagrama")
    }
    return println("Não é anagrama")
}

//Make an Int Array of the ASCII, Make a CharArray of one of the Arrays, Update the IntArray with info of the CharArray and then Compare
fun isPermutationCount(string1: String, string2: String){
    if (string1.length != string2.length){
        return println("Não é anagrama")
    }
    var letters: Array<Int> = Array(128) {0}
    val string1CharArray = string1.toCharArray()

    string1CharArray.forEach { char ->
        letters[char.code]++
    }

    string2.forEach {char->
        letters[char.code]--
        if (letters[char.code] < 0){
            return println("Não é anagrama")
        }
    }
    return println("É anagrama")

}


fun main(){
    val string1: String = "abcdez"
    val string2: String = "zedcb"
    println("Solution 1")
    isPermutationBF(string1, string2)
    println("Solution 2")
    isPermutationCount(string1,string2)

}