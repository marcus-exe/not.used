package grokking_Algorithms

var statesNeeded = setOf("mt", "wa", "or", "id", "nv", "ut", "ca", "az")

val stations = hashMapOf<String, Set<String>>(
    "kone" to setOf("id", "nv", "ut"),
    "ktwo" to setOf("mt", "wa", "id"),
    "kthree" to setOf("or", "nv", "ca"),
    "kfour" to setOf("nv", "ut"),
    "kfive" to setOf("ca", "az")
)

fun greedyAlgorithms() {
    val finalStations: MutableSet<String> = mutableSetOf()
    while (statesNeeded.isNotEmpty()) {
        var bestStation: String? = null
        var statesCovered: Set<String> = setOf()

        stations.forEach { (station, states) ->
            var covered = statesNeeded.intersect(states)
            if (covered.size > statesCovered.size) {
                bestStation = station
                statesCovered = covered
            }
        }
        statesNeeded = statesNeeded - statesCovered
        bestStation?.let { finalStations.add(it) }
    }

    println(finalStations)


}

fun main() {
    greedyAlgorithms()
}