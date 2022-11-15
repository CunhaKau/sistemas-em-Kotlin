import java.lang.IllegalStateException

sealed class Light

data class LList(var head: Light, val tail: LList?)

object Green: Light()
object Yellow: Light()
object Red: Light()

fun next(ls: LList?): LList? =
    if (ls == null) null
    else if (ls.head == Green) {
        ls.head = Yellow
        print(toString(ls.head) + "->")
        next(ls.tail)
    }
    else if (ls.head == Yellow) {
        ls.head = Red
        print(toString(ls.head) + "->")
        next(ls.tail)
    }
    else if (ls.head == Red) {
        ls.head = Green
        print(toString(ls.head) + "->")
        next(ls.tail)
    }
    else throw IllegalStateException()

fun count(ls: LList?, light: Light): Int =
    if (ls == null) 0
    else if (light == ls.head)
        1 + count(ls.tail, light)
    else
        count(ls.tail, light)

fun time(ls: LList?): Int =
    if (ls == null) 1
    else 1 + time(ls.tail)

fun toString(head: Light): String  = when (head) {
    is Green -> "Green"
    is Yellow -> "Yellow"
    is Red -> "Red"
}

fun main (args: Array<String>) {

    val lsc = LList(Green, LList(Red, LList(Yellow, null)))
    println(count(lsc, Green))

    val lsn = LList(Green, LList(Red, LList(Yellow, null)))
    println(next(lsn))

    val lst = LList(Green, LList(Yellow, LList(Green, LList(Red, null))))
    println(time(lst))
}
