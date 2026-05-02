package grokking_Algorithms

import java.util.*



val graph = hashMapOf(
    "You" to listOf<String>("Claire", "Bob", "Alice"),
    "Bob" to listOf<String>("Anuj", "Peggy"),
    "Alice" to listOf<String>("Peggy"),
    "Claire" to listOf<String>("Thom", "Jonny")
)

fun personIsSeller(name: String) : Boolean = name.endsWith("m")

// looking for a more "kotlin way" of doing this
fun breadthFirstSearch(name: String, graph: HashMap<String, List<String>>) : Unit{
    val queue = ArrayDeque(graph[name])
    val searched = arrayListOf<String>()
    while (queue.isNotEmpty()){
        val person = queue.poll()
        if (!searched.contains(person)){
            if (personIsSeller(person)){
                println("$person is a mango seller")
                return
            } else{
                graph[person]?.let{ queue.addAll(it)}
                searched.add(person)
            }
        }
    }
    println("No mango seller found")
}

fun main(){
    val bfs = ::breadthFirstSearch
    bfs("You", graph)

}