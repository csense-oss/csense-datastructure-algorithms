@file:Suppress("unused", "NOTHING_TO_INLINE")

package csense.kotlin.algorithms

import csense.kotlin.extensions.ranges.largest

/**
 * A comparison between 2 elements;
 * if equal (x == y) then "Equal"
 * if x > y then Larger than
 * if x < y then less than.
 */
public enum class ItemComparison {
    /**
     * x > y then Larger than
     */
    GreaterThan,

    /**
     * x < y then less than.
     */
    LessThan,

    /**
     * (x == y) then "Equal"
     */
    Equal
}

//region scoped Int extensions
/**
 * Provides [ItemComparison] features
 * @property int Int
 * @constructor
 */
public inline class IntItemComparison(public val int: Int)

/**
 * Retrieves access to [ItemComparison] features
 */
public inline val Int.itemComparison: IntItemComparison
    get() = IntItemComparison(this)
//endregion

/**
 * Compares a regular "compareTo" into a comparing.
 * @receiver [Int]
 * @return [ItemComparison]
 * @TimeComplexity O(1)
 */
public inline fun IntItemComparison.toComparing(): ItemComparison {
    return when {
        int == 0 -> ItemComparison.Equal
        int > 0 -> ItemComparison.GreaterThan
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
 * @TimeComplexity O(1)
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
 * @TimeComplexity O(1)
 */
public inline fun IntItemComparison.compareToRange(
    intRange: IntRange
): ItemComparison {
    return when {
        int in intRange -> ItemComparison.Equal
        int > intRange.largest -> ItemComparison.GreaterThan
        else -> ItemComparison.LessThan
    }
}