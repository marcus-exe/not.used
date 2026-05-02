//Rotate Matrix
//My first guess is to rotate is by multiplying by another matrix
//It could also be interpreted as the transposition (if on trigonometric 90 instead of clockwise)

fun matrixRotation90(matrix: List<List<Int>>): MutableList<MutableList<Int>> {
    val numRows = matrix.size //the main list is a col, so we use it to get the num of rows
    val numCols = matrix[0].size // that means the first inside list is a row, we use it to get the num of cols
    val rotationMatrix = mutableListOf<MutableList<Int>>()
    for (j in 0 until numCols) {
        val newRow = mutableListOf<Int>()
        for (i in 0 until numRows) {
            newRow.add(matrix[i][j])
        }
        rotationMatrix.add(newRow)
    }
    return rotationMatrix
}

fun main() {
    //initial matrix
    val matrix = listOf(
        listOf(1, 1, 1),
        listOf(2, 2, 2),
        listOf(3, 3, 3)
    )

    matrix.forEach {
        println(it)
    }

    println()

    //rotated 90 matrix
    val rotatedMatrix90 = matrixRotation90(matrix)

    rotatedMatrix90.forEach{
        println(it)
    }

    println()
}

