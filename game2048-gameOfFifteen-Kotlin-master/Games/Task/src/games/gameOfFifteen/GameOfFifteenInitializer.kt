package games.gameOfFifteen

import kotlin.random.Random

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {

        val permutation = List(15){index -> index + 1 }.shuffled().toMutableList()

        if(!isEven(permutation)){
            for (i in 0 until permutation.size-1){
                if(permutation[i] > permutation[i+1]){
                    val temp = permutation[i]
                    permutation[i] = permutation[i+1]
                    permutation[i+1] = temp
                    break
                }
            }
        }

        return@lazy permutation


    }
}

