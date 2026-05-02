//String Compression
fun stringCompression(string: String){
    val setArray = string.toSortedSet() //O(n log(n))
    val hashMap = setArray.associateWith { 0 }.toMutableMap() //O(n)

    string.forEach {char ->
        if (hashMap.containsKey(char)) {
            hashMap[char] = (hashMap[char] ?: 0) + 1
        }
    } //O(n)
    val finalString = buildString {
        for ((key, value) in hashMap) {
            append("$key$value")
        }
    }
    println(finalString)
}

fun main(){
    val string = "ccccbbbaa"
    stringCompression(string)
}