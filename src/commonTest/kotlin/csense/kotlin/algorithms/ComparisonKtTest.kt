package csense.kotlin.algorithms

import csense.kotlin.tests.assertions.assert
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.fail

class ComparisonKtTest {

    @Test
    fun testToComparing() {
        0.itemComparison.toComparing().assert(ItemComparison.Equal)
        // -1 means that x > y thus the comparison is that x is larger than y (y is less than x)
        (-1).itemComparison.toComparing().assert(ItemComparison.LessThan)
        // 1 means that x < y thus the comparison is that y is larger than x.( y is larger than x)
        1.itemComparison.toComparing().assert(ItemComparison.GreaterThan)
        Int.MAX_VALUE.itemComparison.toComparing().assert(ItemComparison.GreaterThan)
        Int.MIN_VALUE.itemComparison.toComparing().assert(ItemComparison.LessThan)
    }

    @Test
    fun compareToRangeFromInclusive() {
        0.itemComparison.compareToRange(0, 1).assert(ItemComparison.Equal)
        1.itemComparison.compareToRange(0, 1).assert(ItemComparison.GreaterThan)
        (-1).itemComparison.compareToRange(0, 1).assert(ItemComparison.LessThan)
        (2).itemComparison.compareToRange(0, 1).assert(ItemComparison.GreaterThan)
    }

    @Test
    fun compareToRangeIntRange() {
        (-1).itemComparison.compareToRange(0 until 5).assert(ItemComparison.LessThan)
        0.itemComparison.compareToRange(0 until 5).assert(ItemComparison.Equal)
        1.itemComparison.compareToRange(0 until 5).assert(ItemComparison.Equal)
        2.itemComparison.compareToRange(0 until 5).assert(ItemComparison.Equal)
        3.itemComparison.compareToRange(0 until 5).assert(ItemComparison.Equal)
        4.itemComparison.compareToRange(0 until 5).assert(ItemComparison.Equal)
        5.itemComparison.compareToRange(0 until 5).assert(ItemComparison.GreaterThan)
    }

    @Test
    fun intItemComparison() {
        (-1).itemComparison.int.assert(-1)
        0.itemComparison.int.assert(0)
        val a = 0.itemComparison
        val b = 1.itemComparison
        assertNotEquals(a, b)
    }

}
