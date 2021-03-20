package csense.kotlin.datastructures.caching

import csense.kotlin.tests.assertions.assert
import csense.kotlin.tests.assertions.assertCalled
import csense.kotlin.tests.assertions.failTest
import kotlin.test.Test

class ValueWithCacheComputationTest {

    class Value {
        @Test
        fun get() {
            val cache = ValueWithCacheComputation("value") {
                failTest("cached values should only be computed on retrieval")
            }
            cache.value.assert("value")
        }

        @Test
        fun updateChangesBothTheValueAndCache() {
            val cache = ValueWithCacheComputation("value") {
                "cache-$it"
            }
            cache.value.assert("value")
            cache.cachedValue.assert("cache-value")
            cache.value = "1234"
            cache.value.assert("1234")
            cache.cachedValue.assert("cache-1234")
        }

        @Test
        fun cacheShouldOnlyBeUpdatedOnUpdates() = assertCalled(times = 2) { shouldBeCalled ->
            val cache = ValueWithCacheComputation("value") {
                shouldBeCalled()
                "cache-$it"
            }
            cache.cachedValue.assert("cache-value")
            cache.cachedValue.assert("cache-value")
            cache.value = "value2"
            cache.cachedValue.assert("cache-value2")
            cache.cachedValue.assert("cache-value2")
        }

        @Test
        fun updateWithSameValueShouldBeIgnored() = assertCalled { shouldBeCalled ->
            val cache = ValueWithCacheComputation("value") {
                shouldBeCalled()
                "cache-$it"
            }
            cache.cachedValue.assert("cache-value")
            cache.value = "value"
            cache.cachedValue.assert("cache-value")
        }
    }

    class CachedValue {
        @Test
        fun get() = assertCalled { shouldBeCalled ->
            val cache = ValueWithCacheComputation("value") {
                shouldBeCalled()
                "cache-$it"
            }
            cache.cachedValue.assert("cache-value")
        }

    }

    @Test
    fun resetToInitial() {
        val cache = ValueWithCacheComputation("value") {
            "cache-$it"
        }
        cache.value.assert("value")
        cache.value = "newValue"
        cache.value.assert("newValue")
        cache.cachedValue.assert("cache-newValue")
        cache.resetToInitial()
        cache.value.assert("value")
        cache.cachedValue.assert("cache-value")
    }
}