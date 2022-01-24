@file:Suppress("unused", "NOTHING_TO_INLINE")

package csense.kotlin.algorithms

import csense.kotlin.extensions.ranges.largest
import kotlin.jvm.*

/**
 * A comparison between 2 elements;
 * if equal (x == y) then "Equal"
 * if x > y then Larger than
 * if x < y then less than.
 */
@JvmInline
public value class ItemComparison private constructor(private val type: Int) : Comparable<ItemComparison> {
    public companion object {

        /**
         * x > y then Larger than
         */
        public val LargerThan: ItemComparison = ItemComparison(1)

        /**
         * x < y then less than.
         */
        public val LessThan: ItemComparison = ItemComparison(-1)

        /**
         * (x == y) then "Equal"
         */
        public val Equal: ItemComparison = ItemComparison(0)
    }

    override fun compareTo(other: ItemComparison): Int =
        this.type.compareTo(other.type)
}

//region scoped Int extensions
/**
 * Provides [ItemComparison] features
 * @property int Int
 * @constructor
 */
@JvmInline
public value class IntItemComparison(public val int: Int)

/**
 * Retrives access to [ItemComparison] features
 */
public inline val Int.itemComparison: IntItemComparison
    get() = IntItemComparison(this)
//endregion

/**
 * Compares a regular "compareTo" into a comparing.
 * @receiver [Int]
 * @return [ItemComparison]
 */
public inline fun IntItemComparison.toComparing(): ItemComparison {
    return when {
        int == 0 -> ItemComparison.Equal
        int > 0 -> ItemComparison.LargerThan
        else -> ItemComparison.LessThan
    }
}


/**
 * Compares this number to a range defined by the given from and to (as a whole),
 * thus its equal iff in range, and then otherwise above or below.
 * @receiver Int
 * @param fromInclusive [Int]
 * @param toInclusive [Int]
 * @return [ItemComparison]
 */
public inline fun IntItemComparison.compareToRange(
    fromInclusive: Int,
    toInclusive: Int
): ItemComparison =
    compareToRange(fromInclusive until toInclusive)


/**
 * Compares this number to a range (as a whole),
 * thus its equal iff in range, and then otherwise above or below.
 * @receiver [Int]
 * @param intRange [IntRange]
 * @return [ItemComparison]
 */
public inline fun IntItemComparison.compareToRange(
    intRange: IntRange
): ItemComparison {
    return when {
        int in intRange -> ItemComparison.Equal
        int > intRange.largest -> ItemComparison.LargerThan
        else -> ItemComparison.LessThan
    }
}