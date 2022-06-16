@file:Suppress("NOTHING_TO_INLINE")

package csense.kotlin.datastructures.values.state

public interface Swappable<T> {
    public fun swap()
    public val value: T
}


public inline fun <T> Swappable<T>.swapAndGetValue(): T {
    swap()
    return value
}