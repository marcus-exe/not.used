import java.lang.StringBuilder
import kotlin.math.abs

//One Away Problem

fun oneAwayProblem(string1: String, string2: String) : String{
    //length check
    if (abs(string1.length - string2.length) > 1)  return "Not One away"
    //equals check
    if (string1 == string2) return "Equal strings"


    //bigger string assign (if exists)
    val biggerString = if (string1.length == string2.length || string1.length > string2.length) {
        string1
    } else {
        string2
    }

    val smallerString = if (string1.length == string2.length || string1.length> string2.length) {
        "$string2 "
    }  else  {
        "$string1 "
    }

    //variables for change
    var changes = 0
    var index1 = 0
    var index2 = 0


    while(index1 < biggerString.length) {
        //println("var1 = ${biggerString[index1]} || var2 = ${smallerString[index2]}")

        //catch difference
        if (biggerString[index1] != smallerString[index2]) {
            changes++

            //conditional for avoiding array out of bound
            if (index1 < biggerString.length - 1){

                //insertion(on bigger string) or deletion (on smaller string)
                if (biggerString[index1 + 1] == smallerString[index2]) {
                    println("Insertion Or Deletion Found")
                    //need to correct the index, if not the code will alert other differences
                    index1++

                } else if (biggerString[index1 + 1] == smallerString[index2 + 1]) {
                    println("Substitution Found")
                    //he returns back to char equivalence after the difference was found naturally
                }
            }
        }
        //check if there is still room for change
        if (changes > 1) return "Not One Away"
        index1++
        index2++
    }
    return "One Away"
}


fun main() {

    val string1 = "caecdd"
    val string2 = "caecdd"

    println(oneAwayProblem(string1, string2))

}
