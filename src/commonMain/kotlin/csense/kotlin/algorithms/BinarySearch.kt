@file:Suppress("unused", "NOTHING_TO_INLINE")

package csense.kotlin.algorithms

import csense.kotlin.Function2
import csense.kotlin.Function1
import csense.kotlin.annotations.numbers.*


/**
 * Performs the binary search algorithm , by providing a custom compare function, that given the current item and the index,
 * yielding the comparison result
 * @receiver [List]<T>
 * @param compareFnc [Function2]<T, [Int], [ItemComparison]>
 * @return [Int]? null if no predicate was equal, or the index if any was found to be equal.
 * @TimeComplexity O(length * log_2(length))
 */
@IntLimit(from = 0)
public inline fun <T> List<T>.binarySearch(crossinline compareFnc: Function2<T, Int, ItemComparison>): Int? =
    GenericAlgorithms.binarySearch(size, this::get, compareFnc)

/**
 * Performs the binary search algorithm , by providing a custom compare function, that given the current item and the index,
 * yielding the comparison result
 * @receiver [Array]<T>
 * @param compareFnc [Function2]<T, [Int], [ItemComparison]>
 * @return [Int]? null if no predicate was equal, or the index if any was found to be equal.
 * @TimeComplexity O(length * log_2(length))
 */
@IntLimit(from = 0)
public inline fun <T> Array<T>.binarySearch(crossinline compareFnc: Function2<T, Int, ItemComparison>): Int? =
    GenericAlgorithms.binarySearch(size, this::get, compareFnc)

/**
 * Performs the binary search algorithm , by providing a custom compare function, that given the current item and the index,
 * yielding the comparison result
 * @receiver [ShortArray]
 * @param compareFnc [Function2]<[Short], [Int], [ItemComparison]>
 * @return [Int]? null if no predicate was equal, or the index if any was found to be equal.
 * @TimeComplexity O(length * log_2(length))
 */
@IntLimit(from = 0)
public inline fun ShortArray.binarySearch(crossinline compareFnc: Function2<Short, Int, ItemComparison>): Int? =
    GenericAlgorithms.binarySearch(size, this::get, compareFnc)

/**
 * Performs the binary search algorithm , by providing a custom compare function, that given the current item and the index,
 * yielding the comparison result
 * @receiver [IntArray]
 * @param compareFnc Function2<[Int], [Int], [ItemComparison]>
 * @return [Int]? null if no predicate was equal, or the index if any was found to be equal.
 * @TimeComplexity O(length * log_2(length))
 */
@IntLimit(from = 0)
public inline fun IntArray.binarySearch(crossinline compareFnc: Function2<Int, Int, ItemComparison>): Int? =
    GenericAlgorithms.binarySearch(size, this::get, compareFnc)

/**
 * Performs the binary search algorithm , by providing a custom compare function, that given the current item and the index,
 * yielding the comparison result
 * @receiver [LongArray]
 * @param compareFnc [Function2]<[Long], [Int], [ItemComparison]>
 * @return [Int]? null if no predicate was equal, or the index if any was found to be equal.
 * @TimeComplexity O(length * log_2(length))
 */
@IntLimit(from = 0)
public inline fun LongArray.binarySearch(crossinline compareFnc: Function2<Long, Int, ItemComparison>): Int? =
    GenericAlgorithms.binarySearch(size, this::get, compareFnc)

/**
 * Performs the binary search algorithm , by providing a custom compare function, that given the current item and the index,
 * yielding the comparison result
 * @receiver [FloatArray]
 * @param compareFnc [Function2]<[Float], [Int], [ItemComparison]>
 * @return [Int]? null if no predicate was equal, or the index if any was found to be equal.
 * @TimeComplexity O(length * log_2(length))
 */
@IntLimit(from = 0)
public inline fun FloatArray.binarySearch(crossinline compareFnc: Function2<Float, Int, ItemComparison>): Int? =
    GenericAlgorithms.binarySearch(size, this::get, compareFnc)

/**
 * Performs the binary search algorithm , by providing a custom compare function, that given the current item and the index,
 * yielding the comparison result
 * @receiver [DoubleArray]
 * @param compareFnc [Function2]<[Double], [Int], [ItemComparison]>
 * @return [Int]? null if no predicate was equal, or the index if any was found to be equal.
 * @TimeComplexity O(length * log_2(length))
 */
@IntLimit(from = 0)
public inline fun DoubleArray.binarySearch(crossinline compareFnc: Function2<Double, Int, ItemComparison>): Int? =
    GenericAlgorithms.binarySearch(size, this::get, compareFnc)


/**
 * Performs the binary search algorithm , by providing a custom compare function, that given the current item and the index,
 * yielding the comparison result
 * The generic algorithm
 * @receiver GenericAlgorithms (to limit the namespace)
 * @param length [Int]
 * @param constantGetter [Function1]<T>
 * @param compareFnc [Function2]<T, [Int], [ItemComparison]>
 * @return [Int]? null if no predicate was equal, or the index if any was found to be equal.
 * @TimeComplexity O(length * log_2(length))
 */

@IntLimit(from = 0)
public inline fun <T> GenericAlgorithms.binarySearch(
    @IntLimit(from = 0) length: Int,
    crossinline constantGetter: (index: @IntLimit(from = 0) Int) -> T,
    crossinline compareFnc: Function2<T, Int, ItemComparison>
): Int? {
    var start = 0
    var end = length
    while (start < end) {
        val mid = start + (end - start) / 2
        val item = constantGetter(mid)
        when (compareFnc(item, mid)) {
            ItemComparison.LessThan -> start = mid + 1
            ItemComparison.LargerThan -> end = mid
            ItemComparison.Equal -> return mid
        }
    }
    return null
}