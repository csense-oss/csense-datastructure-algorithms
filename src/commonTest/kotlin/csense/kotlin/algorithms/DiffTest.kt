package csense.kotlin.algorithms

import csense.kotlin.tests.assertions.*
import kotlin.test.*

class DiffTest {
    class MapKeyValueComputeDiff {
        @Test
        fun empty() {
            val res = mapOf<String, String>().computeDiff(
                other = mapOf(),
                areValuesEqual = { key: String, fromValue: String, toValue: String -> shouldNotBeCalled() }
            )
            res.changedContent.assertEmpty()
            res.sameContent.assertEmpty()
            res.uniqueKeysInFirst.assertEmpty()
            res.uniqueKeysInSecond.assertEmpty()
        }

        @Test
        fun emptyToSingle() {
            val res = mapOf<String, String>().computeDiff(
                other = mapOf("x" to "y"),
                areValuesEqual = { key: String, fromValue: String, toValue: String -> shouldNotBeCalled() }
            )
            res.changedContent.assertEmpty()
            res.sameContent.assertEmpty()
            res.uniqueKeysInFirst.assertEmpty()
            res.uniqueKeysInSecond.assertSingle {
                it.key.assert("x")
                it.value.assert("y")
            }
        }


        @Test
        fun singleToEmpty() {
            val res = mapOf("x" to "y").computeDiff(
                other = mapOf(),
                areValuesEqual = { key: String, fromValue: String, toValue: String -> shouldNotBeCalled() }
            )
            res.changedContent.assertEmpty()
            res.sameContent.assertEmpty()
            res.uniqueKeysInFirst.assertSingle {
                it.key.assert("x")
                it.value.assert("y")
            }
            res.uniqueKeysInSecond.assertEmpty()
        }

        @Test
        fun singleToSingleSeperateKeys() {
            val res = mapOf("first" to "1").computeDiff(
                other = mapOf("second" to "2"),
                areValuesEqual = { key: String, fromValue: String, toValue: String -> shouldNotBeCalled() }
            )
            res.changedContent.assertEmpty()
            res.sameContent.assertEmpty()
            res.uniqueKeysInFirst.assertSingle {
                it.key.assert("first")
                it.value.assert("1")
            }
            res.uniqueKeysInSecond.assertSingle {
                it.key.assert("second")
                it.value.assert("2")
            }
        }

        @Test
        fun singleToSingleSame() {
            val res = mapOf("x" to "y").computeDiff(
                other = mapOf("x" to "2"),
                areValuesEqual = { key: String, fromValue: String, toValue: String ->
                    key.assert("x")
                    fromValue.assert("y")
                    toValue.assert("2")
                    true
                }
            )
            res.changedContent.assertEmpty()
            res.sameContent.assertSingle {
                it.key.assert("x")
                it.value.assert("y", message = "should take the first when its the same")
            }
            res.uniqueKeysInFirst.assertEmpty()
            res.uniqueKeysInSecond.assertEmpty()
        }

        @Test
        fun singleToSingleDifferent() {
            val res = mapOf("x" to "y").computeDiff(
                other = mapOf("x" to "2"),
                areValuesEqual = { key: String, fromValue: String, toValue: String ->
                    key.assert("x")
                    fromValue.assert("y")
                    toValue.assert("2")
                    false
                }
            )
            res.changedContent.assertSingle {
                it.key.assert("x")
                it.value.first.assert("y")
                it.value.second.assert("2")
            }
            res.sameContent.assertEmpty()
            res.uniqueKeysInFirst.assertEmpty()
            res.uniqueKeysInSecond.assertEmpty()
        }


        @Test
        fun multipleAMixtureOfAllCases() {
            val fromMap = mapOf("a" to "1", "b" to "2", "c" to "3")
            val toMap = mapOf("a" to "11", "b" to "2", "d" to "4")

            val diff = fromMap.computeDiff(
                other = toMap,
                areValuesEqual = { key, fromValue, toValue ->
                    fromValue == toValue
                }
            )
            diff.sameContent.assertSingle {
                it.key.assert("b")
                it.value.assert("2")
            }
            diff.changedContent.assertSingle {
                it.key.assert("a")
                it.value.first.assert("1")
                it.value.second.assert("11")
            }

            diff.uniqueKeysInFirst.assertSingle {
                it.key.assert("c")
                it.value.assert("3")
            }
            diff.uniqueKeysInSecond.assertSingle {
                it.key.assert("d")
                it.value.assert("4")
            }
        }

    }
}