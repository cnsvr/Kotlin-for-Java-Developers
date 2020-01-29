package games.game2048

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Your task is to implement the game 2048 https://en.wikipedia.org/wiki/2048_(video_game).
 * Implement the utility methods below.
 *
 * After implementing it you can try to play the game running 'PlayGame2048'.
 */
fun newGame2048(initializer: Game2048Initializer<Int> = RandomGame2048Initializer): Game =
        Game2048(initializer)

class Game2048(private val initializer: Game2048Initializer<Int>) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        repeat(2) {
            board.addNewValue(initializer)
        }
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon() = board.any { it == 2048 }

    override fun processMove(direction: Direction) {
        if (board.moveValues(direction)) {
            board.addNewValue(initializer)
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}

/*
 * Add a new value produced by 'initializer' to a specified cell in a board.
 */
fun GameBoard<Int?>.addNewValue(initializer: Game2048Initializer<Int>) {

    val newValues = initializer.nextValue(this)

    set(newValues!!.first,newValues.second)
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" in a specified rowOrColumn only.
 * Use the helper function 'moveAndMergeEqual' (in Game2048Helper.kt).
 * The values should be moved to the beginning of the row (or column),
 * in the same manner as in the function 'moveAndMergeEqual'.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValuesInRowOrColumn(rowOrColumn: List<Cell>): Boolean {

    var update = false

    val updateCells = rowOrColumn.map { this[it] }.moveAndMergeEqual { value -> value*2 }

    if(updateCells.isNotEmpty()){
        for (i in 0 until updateCells.size){
            val cell = rowOrColumn[i]
            val value = updateCells[i]
            if(this[cell] != value){
                this[cell] = value
                update = true
            }
        }

        for (i in updateCells.size until width){
            this[rowOrColumn[i]] = null
        }
    }

    return update
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" to the specified direction
 * following the rules of the 2048 game .
 * Use the 'moveValuesInRowOrColumn' function above.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValues(direction: Direction): Boolean {

    var moving = false

    when(direction){
        Direction.UP ->{
            for (j in 1..width){
                val column = this.getColumn(1..width,j)
                moving = moveValuesInRowOrColumn(column) or moving
            }
        }

        Direction.DOWN ->{
            for (j in 1..width){
                val column = this.getColumn(width downTo 1,j)
                moving = moveValuesInRowOrColumn(column) or moving
            }
        }

        Direction.LEFT ->{
            for (i in 1..width){
                val row = this.getRow(i,1..width)
                moving = moveValuesInRowOrColumn(row) or moving
            }
        }

        Direction.RIGHT ->{
            for (i in 1..width){
                val row = this.getRow(i,width downTo 1)
                moving = moveValuesInRowOrColumn(row) or moving
            }
        }
    }

    return moving
}



