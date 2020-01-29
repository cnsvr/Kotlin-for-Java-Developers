package nicestring

fun String.isNice(): Boolean {

    /*
    var condition = 0

    if (!this.contains("bu") && !this.contains("ba")&& !this.contains("be")){
        condition++
  }
    if (this.count{ it == 'a' || it == 'e' || it == 'i' || it == 'o' || it == 'u'  }  >= 3){
        condition++
    }

    for (i in 0 until this.length - 1){

        if(this[i] == this[i+1]) {
            condition++
            break

        }
    }
   return condition >= 2

     */

    val noString = setOf("ba","bu","be").none{this.contains(it)}

    val threeVowels = count{it in "aeiou"}>= 3

    var hasDouble = zipWithNext().any{it.first == it.second}

    return listOf<Boolean>(noString,threeVowels,hasDouble).filter { it == true }.size >= 2

}
