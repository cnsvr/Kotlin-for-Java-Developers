package board

import board.Direction.*


open class squareBoardImpl(override val width: Int) : SquareBoard{

    var cells:Array<Array<Cell>> = arrayOf(arrayOf())

    override fun getCellOrNull(i: Int, j: Int): Cell? {

        return if (i>width || j > width || i == 0 ||j == 0){
            null
        }else{
            getCell(i,j)
        }
    }

    override fun getCell(i: Int, j: Int): Cell {

        return if(i>width || j > width || i < 0 ||j < 0){
            throw IllegalArgumentException()

        }else{
            cells[i-1][j-1]
        }
    }
    override fun getAllCells(): Collection<Cell>  = IntRange(1,width).flatMap { i:Int -> IntRange(1,width)
            .map { j:Int -> getCell(i,j) }}
            .toList()

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> =
            when{
                jRange.last > width -> IntRange(jRange.first,width).map { j ->  getCell(i,j) }.toList()
                else -> jRange.map { j -> getCell(i,j) }.toList()
            }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> =
            when{
                iRange.last > width -> IntRange(iRange.first,width).map { i -> getCell(i,j) }.toList()
                else -> iRange.map { i ->  getCell(i,j) }.toList()
            }

    override fun Cell.getNeighbour(direction: Direction): Cell? {

       return when(direction){
            UP -> getCellOrNull(i-1,j)
            DOWN -> getCellOrNull(i+1,j)
            LEFT -> getCellOrNull(i,j-1)
            RIGHT -> getCellOrNull(i,j+1)
        }

    }

}


private fun  createEmptyBoard(width: Int): Array<Array<Cell>>{

    var board : Array<Array<Cell>> = arrayOf()

    for (a in 1..width){
        var array = arrayOf<Cell>()
        for (b in 1..width){
            array += Cell(a,b)


        }
        board += array
    }



    return board
}

fun createSquareBoard(width: Int): SquareBoard  {

    val squareBoard = squareBoardImpl(width)

    squareBoard.cells = createEmptyBoard(width)

    return squareBoard

}

open class gameBoardImpl<T>(override val width: Int) : squareBoardImpl(width),GameBoard<T>{

    val cellValues = mutableMapOf<Cell,T?>()

    override fun get(cell: Cell): T?  = cellValues[cell]

    override fun set(cell: Cell, value: T?) {
        cellValues += cell to value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> = cellValues.filterValues { predicate.invoke(it) }.keys

    override fun find(predicate: (T?) -> Boolean): Cell? = cellValues.filter { predicate.invoke(it.value) }.keys.first()

    override fun any(predicate: (T?) -> Boolean): Boolean  = cellValues.values.any(predicate)

    override fun all(predicate: (T?) -> Boolean): Boolean  = cellValues.values.all(predicate)
}
fun <T> createGameBoard(width: Int): GameBoard<T> {

    val gameBoard = gameBoardImpl<T>(width)

    gameBoard.cells = createEmptyBoard(width)

    gameBoard.cells.forEach { it.forEach { cell -> gameBoard.cellValues += cell to null } }

    return gameBoard
}

