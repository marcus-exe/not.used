//Palindrome Permutation

// This solution is like the first one described in the book, but it has the beauty of Kotlin in it
fun isPalindromePermutationHashMap(string: String) {

    val stringHash = HashMap<Char, Int>()
    string.forEach { char ->
        if (char != ' '){
            stringHash[char] = stringHash.getOrDefault(char, 0) + 1
        }
    }
    val oddChar = stringHash.filter {
        it.value % 2 == 1
    }
    if (oddChar.size > 1){
        println("Cannot be palindrome")
    } else {
        println("Can be palindrome")
    }

}

// I did not develop this one, but it's mentioned in the book
fun isPalindromePermutationBitVector(input: String): Boolean {
    // Create a bit vector to represent character frequencies
    var bitVector = 0

    for (char in input) {
        val charIndex = char.code - 'a'.code

        // Toggle the bit at the corresponding index
        bitVector = bitVector xor (1 shl charIndex)
    }

    // Check if at most one bit is set in the bit vector
    return bitVector == 0 || (bitVector and (bitVector - 1)) == 0
}


fun main() {
    val string = "aaarrss"
    isPalindromePermutationHashMap(string)
    println(isPalindromePermutationBitVector(string))

}