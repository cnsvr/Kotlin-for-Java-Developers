package games.gameOfFifteen

import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {

    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        board.initializeValue(initializer)
    }

    override fun canMove(): Boolean = board.any { it == null }



    override fun hasWon(): Boolean {

        val wonList: MutableList<Int?> = List(15){index -> index+1}.toMutableList()

        wonList.add(null)

        var won = true

        board.getAllCells().forEach {
            val value = wonList[0]
            if(board[it] != value){
                won = false
                }
            wonList.remove(value)
            }

        return won
        }

    override fun processMove(direction: Direction) {

        board.changeValues(direction)

    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }

}

fun GameBoard<Int?>.initializeValue(initializer: GameOfFifteenInitializer){

    val values = initializer.initialPermutation.toMutableList()


    for (cell in getAllCells()){
        if(values.isNotEmpty()) {
            val value = values[0]
            set(cell,value)
            values.remove(value)
        }else{
            set(cell,null)
        }
    }

}

fun GameBoard<Int?>.changeValues(direction: Direction){

    val cell = find { it == null }


    when(direction){
        Direction.UP -> {

            if(cell?.getNeighbour(Direction.DOWN) != null){

                val tempCell = cell.getNeighbour(Direction.DOWN)
                val value = get(tempCell!!)
                set(cell,value)
                set(tempCell,null)
            }

        }

        Direction.DOWN -> {

            if(cell?.getNeighbour(Direction.UP) != null){

                val tempCell = cell.getNeighbour(Direction.UP)
                val value = get(tempCell!!)
                set(cell,value)
                set(tempCell,null)
            }

        }

        Direction.RIGHT -> {


            if(cell?.getNeighbour(Direction.LEFT) != null){

                val tempCell = cell.getNeighbour(Direction.LEFT)
                val value = get(tempCell!!)
                set(cell,value)
                set(tempCell,null)
            }
        }

        Direction.LEFT -> {

            if(cell?.getNeighbour(Direction.RIGHT) != null){

                val tempCell = cell.getNeighbour(Direction.RIGHT)
                val value = get(tempCell!!)
                set(cell,value)
                set(tempCell,null)
            }

        }

    }

}

