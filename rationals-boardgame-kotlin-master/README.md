## Rational Numbers

Your task is to implement a class Rational representing rational numbers. A rational number is a number expressed as a ratio n/d , where n (numerator) and d (denominator) are integer numbers, except that d cannot be zero. If the denominator is zero, you can throw an exception on creating a new instance of the class, e.g. IllegalArgumentException.
Examples of rational numbers are 1/2, 2/3, 117/1098, and 2/1 (which can alternatively be written simply as 2). Rational numbers are represented exactly, without rounding or approximation, which gives them the advantage compared to floating-point numbers.
Your task it to model the behavior of rational numbers, including allowing them to be added, subtracted, multiplied, divided and compared. All arithmetic and comparison operations must be available for rationals: +, -, *, /, ==, <, >= etc. Check whether a number belongs to a range should also be possible: 1/2 in 1/3..2/3 should return true.
The Rational class should contain a numerator and denominator which might be unlimited numbers, so use java.math.BigInteger class for storing them.
The rational numbers must be compared to their "normalized" forms: for example, 1/2 should be equal to 2/4, or 117/1098 to 13/122. The string representation of a rational must be also given in the normalized form. Note that the denominator 1 must be omitted, so 2/1 must be printed as "2". The denominator must be always positive in the normalized form. If the negative rational is normalized, then only the numerator can be negative, e.g. the normalized form of 1/-2 should be -1/2.
Note that you can use BigInteger.gcd to find the greatest common divisor used in the normalized form calculation.
You need to support two ways to create rationals. The first one is to convert a string representation to a rational directly, like in "1/2".toRational(). Converting an integer number should also be possible, and 1 should be used as denominator by default: "23".toRational() is the same as "23/1".toRational().
The alternative way to create a rational is to use divBy infix function like in 1 divBy 2. The receiver and the argument might be of types Int, Long, or BigInteger.
Examples
All the following equality checks must be evaluated to true:
val half = 1 divBy 2
val third = 1 divBy 3

val sum: Rational = half + third
5 divBy 6 == sum

val difference: Rational = half - third
1 divBy 6 == difference

val product: Rational = half * third
1 divBy 6 == product

val quotient: Rational = half / third
3 divBy 2 == quotient

val negation: Rational = -half
-1 divBy 2 == negation

(2 divBy 1).toString() == "2"
(-2 divBy 4).toString() == "-1/2"
"117/1098".toRational().toString() == "13/122"

val twoThirds = 2 divBy 3
half < twoThirds

half in third..twoThirds

2000000000L divBy 4000000000L == 1 divBy 2

"912016490186296920119201192141970416029".toBigInteger() divBy
    "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2




## Board


Your task is to implement interfaces SquareBoard and GameBoard.
SquareBoard
SquareBoard stores the information about the square board and all the cells on it. It allows the retrieval of a cell by its indexes, parts of columns and rows on a board, or a specified neighbor of a cell.
Note that the numbering of cells starts with 1 (not 0).
A board of width two consists of the following cells:
(1, 1) (1, 2)
(2, 1) (2, 2)

For the following examples, we'll use this board of width 2:
val board = createSquareBoard(2)

If you call board.getCellOrNull(3, 3) for such a board, you'll get null as the result, because the board doesn't have a cell with such coordinates. The function Board.getCell should throw IllegalArgumentException for incorrect values of i and j.
You can write board.getRow(1, 1..2) or board.getRow(1, 2 downTo 1), and you'll get the lists of cells [(1, 1), (1, 2)] and [(1, 2), (1, 1)] accordingly.
Note how using the range 2 downTo 1 returns a row in a reversed order. You can use any range to get a part of a column or a row.
Note that getRow and getColumn should return a list containing only the cells that belong to the board if the range is larger than the board limits and ignore other indexes, thus, board.getRow(1, 1..10) should return [(1, 1), (1, 2)].
The neighbors of a cell (1, 1), depending on the direction should be:
Direction.UP - null     
Direction.LEFT - null     
Direction.DOWN - (2, 1) 
Direction.RIGHT - (1, 2)

Create only width * width cells; all the functions working with cells should return existing cells instead of creating new ones.
GameBoard
GameBoard allows you to store values in board cells, update them, and enquire about stored values (like any, all etc.) Note that GameBoard extends SquareBoard.
Don't store a value in a Cell: data class Cell is intended to be immutable and only store the coordinates. You can store values separately, for instance, in a map from Cell to stored values type.
See TestSquareBoard and TestGameBoard for examples.
