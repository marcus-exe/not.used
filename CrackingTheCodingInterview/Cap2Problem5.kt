import kotlin.math.pow
fun main(){
    val list1 = listOf(1.0, 2.0, 3.0, 4.0)
    val list2 = listOf(1.0, 2.0, 3.0, 4.0)
    val list3 = sumUsingLists(list1, list2)
    println("""
        List1: ${list1} that is equivalent to : 1234
        List2: ${list2} that is equivalent to : 1234
        Sum of List 1 and List 2:  ${list3} that should be equivalent to 2468
    """.trimIndent())
}
fun sumUsingLists(list1: List<Double>, list2: List<Double>) : List<Char>{
    var list1Number = 0.0
    var list2Number = 0.0
    val base = 10.0
    list1.reversed().forEachIndexed { index, decimal ->
        list1Number += decimal * base.pow(index)
    }

    list2.reversed().forEachIndexed { index, decimal ->
        list2Number += decimal * base.pow(index)
    }
    val sum = list1Number + list2Number
    val outputList = mutableListOf<Char>()


    sum.toString().forEach { decimal ->
        if (decimal == '.') {
            return outputList
        }
        outputList.add(decimal)
    }

    return outputList
}
