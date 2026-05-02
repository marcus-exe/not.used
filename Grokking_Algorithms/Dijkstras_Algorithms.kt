package grokking_Algorithms

import kotlin.Double.Companion.POSITIVE_INFINITY


var costs = hashMapOf<String, Double>(
    "A" to 6.0,
    "B" to 2.0,
    "END" to POSITIVE_INFINITY
)

var parents = hashMapOf<String, String?>(
    "A" to "START",
    "B" to "START",
    "END" to null
)
val grafo = hashMapOf<String, HashMap<String, Double>?>(
    "START" to hashMapOf<String, Double>("A" to 6.0, "B" to 2.0),
    "A" to hashMapOf<String, Double>("END" to 1.0),
    "B" to hashMapOf<String, Double>("A" to 3.0, "END" to 5.0),
    "END" to null
)

var processed: MutableList<String?> = mutableListOf(null)

fun findSmallestCost(costs: HashMap<String, Double>): String? {
    var smallestCost: Double = POSITIVE_INFINITY
    var nodeSmallestCost: String? = null
    costs.forEach { (node, cost) ->
        if (cost < smallestCost && node !in processed) {
            smallestCost = cost
            nodeSmallestCost = node
        }
    }
    return nodeSmallestCost
}


fun Dijkstras_Algorithms() {
    var nodeSmallestCost: String? = findSmallestCost(costs)
    while (nodeSmallestCost != null) {
        var cost: Double? = costs[nodeSmallestCost]
        val neighbors: HashMap<String, Double>? = grafo[nodeSmallestCost]
        neighbors?.forEach { (node, value) ->
            val newCost = cost?.plus(value)
            if (costs[node]!! > newCost!!) {
                costs[node] = newCost
                parents[node] = nodeSmallestCost
            }
        }
        processed.add(nodeSmallestCost)
        nodeSmallestCost = findSmallestCost(costs)
    }
}


fun main() {
    println(costs)
    Dijkstras_Algorithms()
    println(costs)
}