//URLify

fun URLyfy(string: String, numberOfChar: Int){
    //trimming
    val trimmedString = string.trim()

    //creating lists
    val newCharArray = mutableListOf<Char>()
    val listOfChar = listOf('%', '2', '0')
    //iterating for existing list and adding stuff to the new one
    for (i in trimmedString.indices){
        if (trimmedString[i] == ' '){
            newCharArray.addAll(listOfChar)
        } else {
            newCharArray.add(trimmedString[i])
        }
    }
    //making it string again
    val URLyfiedString = newCharArray.joinToString(separator = "")
    return println(URLyfiedString)

}

fun main() {
    val string = "Mr John Smith    "
    val numberOfChar = 13
    URLyfy(string, numberOfChar)
}


/* book solution
1) Iterate through the char array once looking for spaces (n)
2) Create an empty array with the right memory size (1)
3) Iterate through the char array once adding all the stuff (n)
*/

/* my solution (doesn't need expected size)
1) create an empty charList(1)
2) Iterate through the String once adding all the stuff(n)
 - list have 0(1) for adding stuff
3) make it back into string (n)
*/