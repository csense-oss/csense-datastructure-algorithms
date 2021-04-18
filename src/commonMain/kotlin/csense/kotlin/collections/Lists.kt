package csense.kotlin.collections

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract


public sealed class Lists<T> {
    public companion object {
        public inline fun <reified T> create(iterable: Iterable<T>): Lists<T> {
            val iterator = iterable.iterator()
            return if (iterator.hasNext()) {
                val first = iterator.next()
                NonEmptyList(first, iterator)
            } else {
                EmptyList()
            }
        }
    }

    @OptIn(ExperimentalContracts::class)
    public fun isEmpty(): Boolean {
        contract {
            returns(true) implies (this@Lists is EmptyList<T>)
        }
        return when (this) {
            is EmptyList -> true
            is NonEmptyList -> false
        }
    }

    @OptIn(ExperimentalContracts::class)
    public fun isNotEmpty(): Boolean {
        contract {
            returns(true) implies (this@Lists is NonEmptyList<T>)
        }
        return when (this) {
            is EmptyList -> false
            is NonEmptyList -> true
        }
    }
}

public class EmptyList<T> : Lists<T>()

public class NonEmptyList<T> private constructor(
    private val items: List<T>
) : List<T>, Lists<T>() {
    public constructor(item: T) : this(listOf(item))
    public constructor(item: T, iterator: Iterator<T>) : this(listOf(item) + iterator.toList())
    public constructor(item: T, other: Collection<T>) : this(listOf(item) + other)

    override val size: Int
        get() = items.size

    override fun contains(element: T): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(index: Int): T {
        TODO("Not yet implemented")
    }

    override fun indexOf(element: T): Int {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<T> {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: T): Int {
        TODO("Not yet implemented")
    }

    override fun listIterator(): ListIterator<T> {
        TODO("Not yet implemented")
    }

    override fun listIterator(index: Int): ListIterator<T> {
        TODO("Not yet implemented")
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<T> {
        TODO("Not yet implemented")
    }

}

public inline fun <reified T> Collection<T>.toLists(): Lists<T> {
    return Lists.create(this)
}


//TODO delete when csense kotlin is 0.0.47


public inline fun <T> Iterator<T>.toList(): List<T> =
    toMutableList()

public inline fun <T, R> Iterator<T>.map(
    transform: (T) -> R
): List<R> = mutableListOf<R>().also { list ->
    this.forEach {
        list += transform(it)
    }
}

public inline fun <T> Iterator<T>.toMutableList(

): MutableList<T> = mutableListOf<T>().also { list ->
    this.forEach {
        list += it
    }
}
