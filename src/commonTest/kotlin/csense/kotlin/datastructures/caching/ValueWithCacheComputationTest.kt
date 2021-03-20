package csense.kotlin.datastructures.caching

import csense.kotlin.tests.assertions.assert
import csense.kotlin.tests.assertions.assertCalled
import csense.kotlin.tests.assertions.failTest
import kotlin.test.Test

class ValueWithCacheComputationTest {

    @Test
    fun getValue() {
        val cache = ValueWithCacheComputation("value") {
            failTest("cached values should only be computed on retrieval")
        }
        cache.getValue().assert("value")
    }

    @Test
    fun getCachedValue() = assertCalled { shouldBeCalled ->
        val cache = ValueWithCacheComputation("value") {
            shouldBeCalled()
            "cache-$it"
        }
        cache.getCachedValue().assert("cache-value")
    }


    class Update {

        @Test
        fun updateChangesBothTheValueAndCache() {
            val cache = ValueWithCacheComputation("value") {
                "cache-$it"
            }
            cache.getValue().assert("value")
            cache.getCachedValue().assert("cache-value")
            cache.update("1234")
            cache.getValue().assert("1234")
            cache.getCachedValue().assert("cache-1234")
        }

        @Test
        fun cacheShouldOnlyBeUpdatedOnUpdates() = assertCalled(times = 2) { shouldBeCalled ->
            val cache = ValueWithCacheComputation("value") {
                shouldBeCalled()
                "cache-$it"
            }
            cache.getCachedValue().assert("cache-value")
            cache.getCachedValue().assert("cache-value")
            cache.update("value2")
            cache.getCachedValue().assert("cache-value2")
            cache.getCachedValue().assert("cache-value2")
        }

        @Test
        fun updateWithSameValueShouldBeIgnored() = assertCalled { shouldBeCalled ->
            val cache = ValueWithCacheComputation("value") {
                shouldBeCalled()
                "cache-$it"
            }
            cache.getCachedValue().assert("cache-value")
            cache.update("value")
            cache.getCachedValue().assert("cache-value")
        }

    }


    class Reset {
        @Test
        fun shouldResetToInitialValues() {
            val cache = ValueWithCacheComputation("value") {
                "cache-$it"
            }
            cache.getValue().assert("value")
            cache.update("newValue")
            cache.getValue().assert("newValue")
            cache.getCachedValue().assert("cache-newValue")
            cache.reset()
            cache.getValue().assert("value")
            cache.getCachedValue().assert("cache-value")
        }

    }
}