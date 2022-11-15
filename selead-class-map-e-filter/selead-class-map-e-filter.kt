sealed class IFList(val size: Int) {
    fun map(f: (Int) -> Int): IFList = when (this) {
        is Null -> Null
        is FList -> FList(f(head), tail.map(f))
    }

    fun filter(p: (Int) -> Boolean): IFList = when (this) {
        is Null -> Null
        is FList -> if (p(head)) FList(head, tail.filter(p)) else tail.filter(p)
    }

    override fun toString(): String = when (this) {
        is Null -> "Null"
        is FList -> "FList($head, $tail)"
    }
}

object Null: IFList(0){

}

data class FList(val head: Int, val tail: IFList): IFList(1+ tail.size) {

}
fun main (args: Array<String>) {
    val ls = FList(2, FList(4, FList(5, Null)))
    println(ls.map{ x -> x * x })
    println(ls.filter{ x -> x > 2 })
}
