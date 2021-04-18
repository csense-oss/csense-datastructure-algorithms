package csense.kotlin.algorithms

import csense.kotlin.tests.assertions.*
import kotlin.test.*

class BinarySearchKtTest {

    class BinarySearchLength {
        /**
         * Highlights the reason why this is there.
         * The use case is more complex data than a simple array of eg ints, where a custom way to compare elements is desirably.
         * also the extension is on array types, which the STD algorithm is not.
         */
        @Test
        fun binarySearchComplexData() {
            //constructed in order  otherwise binary search does not work..
            val array =
                arrayOf(RandomComplexData("Test1", 0), RandomComplexData("Test2", 1), RandomComplexData("Test3", 2))
            //we want to find Test2, and
            val result = array.binarySearch { data, _ ->
                data.name.compareTo("Test2").itemComparison.toComparing()
            }
            result.assertNotNullAndEquals(1)
        }

        data class RandomComplexData(val name: String, val order: Int)


        @Test
        fun binarySearch() {
            val strArray = arrayListOf("a", "b", "c", "d")
            val indexOfA = strArray.binarySearch { item: String, _: Int ->
                item.compareTo("a").itemComparison.toComparing()
            }
            indexOfA.assertNotNullAndEquals(0)

            val indexOfB = strArray.binarySearch { item: String, _: Int ->
                item.compareTo("b").itemComparison.toComparing()
            }
            indexOfB.assertNotNullAndEquals(1)

            val indexOfq = strArray.binarySearch { item: String, _: Int ->
                item.compareTo("q").itemComparison.toComparing()
            }
            indexOfq.assertNull()


            val indexOfIndex = strArray.binarySearch { _: String, index: Int ->
                (index - 3).itemComparison.toComparing()
            }
            indexOfIndex.assertNotNullAndEquals(3)


        }

    }


    class ListTBinarySearchCompareFnc {
        @Test
        fun empty() {
            listOf<Int>().binarySearch { _, _ -> shouldNotBeCalled() }.assertNull()
        }

        @Test
        fun single() {
            listOf("0").binarySearch { _, _ ->
                ItemComparison.Equal
            }.assertNotNullAndEquals(0)

            listOf("0").binarySearch { _, _ ->
                ItemComparison.LessThan
            }.assertNull()

            listOf("0").binarySearch { _, _ ->
                ItemComparison.GreaterThan
            }.assertNull()
        }

        @Test
        fun multiple() {
            //TODO test multiple element condition here.
        }
    }

    class ArrayTBinarySearchCompareFnc {
        @Test
        fun empty() {
            arrayOf<Int>().binarySearch { _, _ -> shouldNotBeCalled() }.assertNull()
        }

        @Test
        fun single() {
            arrayOf("0").binarySearch { _, _ ->
                ItemComparison.Equal
            }.assertNotNullAndEquals(0)

            arrayOf("0").binarySearch { _, _ ->
                ItemComparison.LessThan
            }.assertNull()

            arrayOf("0").binarySearch { _, _ ->
                ItemComparison.GreaterThan
            }.assertNull()
        }

        @Test
        fun multiple() {
            //TODO test multiple element condition here.
        }
    }

    class ShortArrayBinarySearchCompareFnc() {
        @Test
        fun empty() {
            shortArrayOf().binarySearch { _, _ -> shouldNotBeCalled() }.assertNull()
        }

        @Test
        fun single() {
            shortArrayOf(0).binarySearch { _, _ ->
                ItemComparison.Equal
            }.assertNotNullAndEquals(0)

            shortArrayOf(0).binarySearch { _, _ ->
                ItemComparison.LessThan
            }.assertNull()

            shortArrayOf(0).binarySearch { _, _ ->
                ItemComparison.GreaterThan
            }.assertNull()
        }

        @Test
        fun multiple() {
            //TODO test multiple element condition here.
        }
    }


    class IntArrayBinarySearchCompareFnc() {
        @Test
        fun empty() {
            intArrayOf().binarySearch { _, _ -> shouldNotBeCalled() }.assertNull()
        }

        @Test
        fun single() {
            intArrayOf(0).binarySearch { _, _ ->
                ItemComparison.Equal
            }.assertNotNullAndEquals(0)

            intArrayOf(0).binarySearch { _, _ ->
                ItemComparison.LessThan
            }.assertNull()

            intArrayOf(0).binarySearch { _, _ ->
                ItemComparison.GreaterThan
            }.assertNull()
        }

        @Test
        fun multiple() {
            //TODO test multiple element condition here.
        }
    }

    class LongArrayBinarySearchCompareFnc() {
        @Test
        fun empty() {
            longArrayOf().binarySearch { _, _ -> shouldNotBeCalled() }.assertNull()
        }

        @Test
        fun single() {
            longArrayOf(0).binarySearch { _, _ ->
                ItemComparison.Equal
            }.assertNotNullAndEquals(0)

            longArrayOf(0).binarySearch { _, _ ->
                ItemComparison.LessThan
            }.assertNull()

            longArrayOf(0).binarySearch { _, _ ->
                ItemComparison.GreaterThan
            }.assertNull()
        }

        @Test
        fun multiple() {
            //TODO test multiple element condition here.
        }

    }

    class FloatArrayBinarySearchCompareFnc() {
        @Test
        fun empty() {
            floatArrayOf().binarySearch { _, _ -> shouldNotBeCalled() }.assertNull()
        }

        @Test
        fun single() {
            floatArrayOf(0.0f).binarySearch { _, _ ->
                ItemComparison.Equal
            }.assertNotNullAndEquals(0)

            floatArrayOf(0.0f).binarySearch { _, _ ->
                ItemComparison.LessThan
            }.assertNull()

            floatArrayOf(0.0f).binarySearch { _, _ ->
                ItemComparison.GreaterThan
            }.assertNull()
        }

        @Test
        fun multiple() {
            //TODO test multiple element condition here.
        }

    }

    class DoubleArrayBinarySearchCompareFnc() {
        @Test
        fun empty() {
            doubleArrayOf().binarySearch { _, _ -> shouldNotBeCalled() }.assertNull()
        }

        @Test
        fun single() {
            doubleArrayOf(0.0).binarySearch { _, _ ->
                ItemComparison.Equal
            }.assertNotNullAndEquals(0)

            doubleArrayOf(0.0).binarySearch { _, _ ->
                ItemComparison.LessThan
            }.assertNull()

            doubleArrayOf(0.0).binarySearch { _, _ ->
                ItemComparison.GreaterThan
            }.assertNull()
        }

        @Test
        fun multiple() {
            //TODO test multiple element condition here.
        }

    }
}