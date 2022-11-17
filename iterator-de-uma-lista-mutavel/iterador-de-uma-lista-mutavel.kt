
import java.lang.Exception

class InvalidNumberOfCopies: Exception()

//empacotou um item que era mutável para um item imutável
class Item<A>(val copies: Int, val key: A){
    operator fun component1() = copies
    operator fun component2() = key
    override fun toString(): String =
        "( $key )"
}
// vararg iterador que vai transformar em um array
class MutableMSet<A>(vararg keys: A): Iterable<Item<A>> {

    //private constructor(newList: MutableListOf<MutableItem<A>>): this{

    //}
    private class MutableItem<A>(var copies: Int, var key: A) {
        operator fun component1() = copies
        operator fun component2() = key
        override fun toString(): String =
            "($copies,$key )"

        fun toItem() = Item(copies, key)

    }

    //suponha que key é uma das chaves de algum item de list
    //então existe um único item em list cuja chave é key e cujo numero de copias é >0
    //Logo, [(3, "caneta"), (5, "papel"), (2, "caneta")], viola o invariante
    private val list = mutableListOf<MutableItem<A>>()

    private var elements = 0

    init {
        for (key in keys) add(key)
    }

    val size: Int
        get() = elements

    fun add(key: A): Unit { //Unit é o mesmo que void
        ++elements
        val item = list.find { it.key == key }
        if (item == null) list.add(MutableItem(1, key))
        else ++item.copies
    }

    fun add(vararg keys: A) {
        for (key in keys) add(key)
    }

    fun add(item: Item<A>) {
        if (item.copies < 0) throw InvalidNumberOfCopies()
        elements += item.copies
        val mutItem = list.find { it.key == item.key }
        if (mutItem == null) list.add(MutableItem(item.copies, item.key))
        else mutItem.copies += item.copies
    }

    fun copies(key: A): Int {
        val item = list.find { it.key == key }
        return item?.copies ?: 0// o mesmo que return if (item==null) 0 else item.copies
    }

    fun remove(key: A): Boolean {
        val item = list.find { it.key == key } ?: return false // ?: = é o mesmo que if(item==null)
        --item.copies
        --elements
        if (item.copies == 0) {
            item.copies = list.last().copies
            item.key = list.last().key
            list.removeLast()
        }
        return true
    }

    //elemento com maior número de copias
    fun max(): Item<A>? {
        val mutItem = list.maxByOrNull { it.copies } ?: return null
        return mutItem.toItem()
    }

    fun find(key: A): Item<A> {
        val mutItem = list.find { it.key == key } ?: return Item(0, key)
        //?: = é o mesmo que if (item == null) return Item(0, key)
        return mutItem.toItem()
    }

    override fun iterator(): Iterator<Item<A>> = MyIt()

    //internamente ele armazena uma referencia para outer class
    private inner class MyIt : Iterator<Item<A>> {
        var current = 0
        var copies = 0
        override fun hasNext(): Boolean = current < list.size

        override fun next(): Item<A> = if (list[current].copies > 1) {
            list[current].copies -= 1
            list[current].toItem()
        } else list[current++].toItem()


        fun filter(p: (A) -> Boolean): MutableMSet<A> {
            val rs = MutableMSet<A>()
            for (mutItem in list.filter { item -> p(item.key) }) rs.add(mutItem.toItem())
            return rs
        }
    }


}
fun main(args: Array<String>) {
    val ms = MutableMSet("caneta", "caneta", "lapis", "lapis")
    ms.add("caderno", "lapis");
    val iter = ms.iterator()
    while (iter.hasNext()) {
        print(iter.next())
    }
}
