package tictactoe

fun printBoard(board: List<Char>) {
    println("---------")
    for (i in board.chunked(3)) {
        println("| ${i.joinToString(" ")} |")
    }
    println("---------")
}

fun makeMove(board: MutableList<Char>, input: String, player: Char) {
    if (!input.matches("""\d+\s\d+""".toRegex())) {
        println("You should enter numbers!")
        return makeMove(board, readln(), player)
    }
    val (x, y) = input.split(" ").map { it.toInt() }
    if (x !in 1..3 || y !in 1..3) {
        println("Coordinates should be from 1 to 3!")
        return makeMove(board, readln(), player)
    }
    val move = 3 * (x - 1) + (y - 1)
    if (board[move] != '_') {
        println("This cell is occupied! Choose another one!")
        return makeMove(board, readln(), player)
    }
    board[move] = player
}

fun checkResult(board: MutableList<Char>): Boolean {
    val str = "012-345-678-036-147-258-048-246".split("-") // winning conditions
    var winner = ' '
    var winnerCount = 0
    for (i in str) {
        val index = i.split("").filter { it != "" }.map { it.toInt() }
        if (board[index[0]] == board[index[1]] && board[index[1]] == board[index[2]] && board[index[0]] != '_') {
            winner = board[index[0]]
            winnerCount++
        }
    }

    if (winnerCount == 1) println("$winner wins").also { return true}
    if (!board.contains('_')) println("Draw").also { return true}
    return false
}

fun main() {
    val board = MutableList<Char>(9) { '_' }
    var turn = true
    printBoard(board)

    while (true) {
        if (turn) {
            makeMove(board, readln(), 'X')
            turn = false
        } else {
            makeMove(board, readln(), 'O')
            turn = true
        }
        printBoard(board)
        if (checkResult(board)) break
    }
}