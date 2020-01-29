package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {


    var rightPosition = 0
    var wrongPosition = 0

    //val rightPositions = secret.zip(guess)

    val secretList = mutableListOf<Char>()
    val guessList = mutableListOf<Char>()

    for(index in 0 until secret.length){
        if(secret[index]==guess[index]){
            rightPosition++

        }else{
            secretList.add(secret[index])
            guessList.add(guess[index])
        }
    }

    val wrongSecret = secretList.toString()
    val wrongGuess = guessList.toString()


    guessList.forEach {
        if(secretList.contains(it) && (wrongGuess.indexOf(it) != wrongSecret.indexOf(it))){
            wrongPosition++
            secretList.remove(it)
    }
    }

    return Evaluation(rightPosition,wrongPosition)

  //  TODO("Do sth.")
}
