package csense.kotlin.collections

import csense.kotlin.extensions.collections.list.addAll
import csense.kotlin.extensions.map
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

public sealed class MutableLists<T> {

    @OptIn(ExperimentalContracts::class)
    public fun isEmpty(): Boolean {
        contract {
            returns(true) implies (this@MutableLists is MutableEmpty<T>)
        }
        return when (this) {
            is MutableEmpty -> true
            is MutableNonEmptyList -> false
        }
    }

    @OptIn(ExperimentalContracts::class)
    public fun isNotEmpty(): Boolean {
        contract {
            returns(true) implies (this@MutableLists is MutableNonEmptyList<T>)
        }
        return when (this) {
            is MutableEmpty -> false
            is MutableNonEmptyList -> true
        }
    }
}

public class MutableEmpty<T>() : MutableLists<T>() {
    public fun add(item: T): MutableNonEmptyList<T> =
        MutableNonEmptyList(item)

    public fun add(list: Lists<T>): MutableLists<T> {
        TODO()
    }

    public fun add(list: MutableLists<T>): MutableLists<T> {
        TODO()
    }

    public fun add(list: MutableEmpty<T>): MutableEmpty<T> {
        TODO()
    }

    public fun add(list: MutableNonEmptyList<T>): MutableLists<T> {
        TODO()
    }

    public fun add(list: List<T>): MutableLists<T> {
        TODO()
    }

}

public class MutableNonEmptyList<T> private constructor(
    private val list: MutableList<T>
) : List<T>, MutableLists<T>() {
    public constructor(item: T) : this(mutableListOf(item))

    override val size: Int
        get() = list.size

    override fun contains(element: T): Boolean = list.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = list.containsAll(elements)

    override fun get(index: Int): T = list[index]

    override fun indexOf(element: T): Int = list.indexOf(element)

    override fun iterator(): MutableIterator<T> = list.iterator()

    override fun lastIndexOf(element: T): Int = list.lastIndexOf(element)
    override fun listIterator(): ListIterator<T> = list.listIterator()

    override fun listIterator(index: Int): ListIterator<T> = list.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): List<T> = list.subList(fromIndex, toIndex)

    //region add methods
    public fun add(element: T): MutableNonEmptyList<T> = apply {
        list.add(element)
    }

    public fun add(index: Int, element: T): MutableNonEmptyList<T> = apply {
        list.add(index, element)
    }

//    public fun addAll(vararg items: T): MutableNonEmptyList<T> = apply {
//        list.addAll(items)
//    }

    public fun addAll(elements: Collection<T>): MutableNonEmptyList<T> = apply {
        list.addAll(elements)
    }

    public fun addAll(index: Int, collection: Collection<T>): MutableNonEmptyList<T> = apply {
        list.addAll(index, collection)
    }

    public fun addAll(elements: Iterable<T>): MutableNonEmptyList<T> = apply {
        list.addAll(elements)
    }

    public fun addAll(index: Int, elements: Iterable<T>): MutableNonEmptyList<T> = apply {
        list.addAll(index, elements)
    }

    public fun addAll(elements: Sequence<T>): MutableNonEmptyList<T> = apply {
        list.addAll(elements)
    }

    public fun addAll(elements: Array<out T>): MutableNonEmptyList<T> = apply {
        list.addAll(elements)
    }
    //endregion

//    public fun remove(element: T): MutableLists<T> {
//        return
//    }
//
//
//    public fun map(): NonEmptyList<T> {
//
//    }
//
//    public fun filter(): Lists<T> {
//
//    }
//
//    public fun filterNot(): Lists<T> {
//
//    }
//
//    public fun mapNot(): NonEmptyList<T> {
//
//    }

}