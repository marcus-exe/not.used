//Rotated String

fun stringRotation(string1: String, string2: String){
    if (string1.length == string2.length) {
        val concatString = string2 + string2
        if (concatString.contains(string1)) {
            return println("$string2 is $string1 in reverse order")
        }
    }
    return println("$string2 is not $string1 in reverse order")
}
fun main(){
    val string = "waterbottle"
    val rotatedString = "erbottlewat"
    stringRotation(string, rotatedString)
}