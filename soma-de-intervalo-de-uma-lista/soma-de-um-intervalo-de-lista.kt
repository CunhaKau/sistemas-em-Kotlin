fun somaListaRangev1(xs ListInt) Int {
    fun soma(b Int = 0, e Int = xs.size) Int =
        if (e - b == 0) 0
        else {
           soma(b, e - 1) + xs[e - 1]
        }
    return soma()


}

fun somaListaRangev2(xs ListInt) Int {
    fun soma(b Int = 0, e Int = xs.size) Int =
        if (e - b == 0) 0
        else {
            soma(b + 1, e) + xs[b]
        }
        return soma()
    }



    fun main(args ArrayString) {
        val xs = listOf(1, 2, 4, 5, 7, 8)
        println(somaListaRangev1(xs))
        println(somaListaRangev2(xs))

    }
