package rationals

import java.math.BigInteger


class Rational(val n: BigInteger,val d:BigInteger) : Comparable<Rational> {


    operator fun plus(rational: Rational) = (n.times(rational.d).plus(rational.n.times(d))).divBy(rational.d.times(d))
    operator fun minus(rational: Rational) = (n.times(rational.d).minus(rational.n.times(d))).divBy(rational.d.times(d))
    operator fun div(rational: Rational) = n.times(rational.d).divBy(d.times(rational.n))
    operator fun times(rational: Rational) = n.times(rational.n).divBy(d.times(rational.d))
    operator fun unaryMinus() = Rational(n.negate(), d)
    override fun compareTo(other: Rational): Int {
        return n.times(other.d).compareTo(other.n.times(d))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as Rational

        val thisNumerator = simplify(this)
        val otherN = simplify(other)

        return thisNumerator.n.toDouble().div(thisNumerator.d.toDouble()) == otherN.n.toDouble().div(otherN.d.toDouble())

    }


    override fun toString(): String {
        return if (d == 1.toBigInteger() || n.rem(d) == 0.toBigInteger()) {
            n.div(d).toString()
        } else {
            val simple = simplify(this)

            if (simple.d < 0.toBigInteger() || (simple.n < 0.toBigInteger() && simple.d < 0.toBigInteger())) {

                stringRational(Rational(simple.n.negate(), simple.d.negate()))
            } else {

                stringRational(Rational(simple.n, simple.d))
            }
        }
    }
}


    /* simplifying with greatest common divisor. */
    fun simplify(r1: Rational): Rational {
        val gcd = r1.d.gcd(r1.n)
        return Rational(r1.n.div(gcd), r1.d.div(gcd))
    }

    fun stringRational(rational: Rational): String = rational.n.toString() + "/" + rational.d.toString()

    infix fun Int.divBy(i: Int): Rational = Rational(toBigInteger(), i.toBigInteger())
    infix fun Long.divBy(l: Long): Rational = Rational(toBigInteger(), l.toBigInteger())
    infix fun BigInteger.divBy(b: BigInteger): Rational = Rational(this, b)

    fun String.toRational(): Rational {
        val numbers = this.split("/")

        return if (numbers.size == 1) {
            Rational(numbers[0].toBigInteger(), 1.toBigInteger())
        } else {
            Rational(numbers[0].toBigInteger(), numbers[1].toBigInteger())
        }
    }


    fun main() {

        val half = 1 divBy 2
        val third = 1 divBy 3

        val sum: Rational = half + third
        println(5 divBy 6 == sum)

        val difference: Rational = half - third
        println(1 divBy 6 == difference)

        val product: Rational = half * third
        println(1 divBy 6 == product)

        val quotient: Rational = half / third
        println(3 divBy 2 == quotient)

        val negation: Rational = -half
        println(-1 divBy 2 == negation)

        println((2 divBy 1).toString() == "2")
        println((-2 divBy 4).toString() == "-1/2")
        println("117/1098".toRational().toString() == "13/122")

        val twoThirds = 2 divBy 3

        println(half < twoThirds)

        println(half in third..twoThirds)

        println(2000000000L divBy 4000000000L == 1 divBy 2)

        println("912016490186296920119201192141970416029".toBigInteger() divBy
                "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)

    }


