package csense.kotlin.datastructures.caching

import csense.kotlin.tests.assertions.assert
import csense.kotlin.tests.assertions.assertCalled
import csense.kotlin.tests.assertions.failTest
import kotlin.test.Test

class CacheAbleTest {

    @Test
    fun getValue() {
        val cache = CacheAble("value") {
            failTest("cached values should only be computed on retrieval")
        }
        cache.getValue().assert("value")
    }

    @Test
    fun getCachedValue() = assertCalled { shouldBeCalled ->
        val cache = CacheAble("value") {
            shouldBeCalled()
            "cache-$it"
        }
        cache.getCachedValue().assert("cache-value")
    }


    class Update {

        @Test
        fun updateChangesBothTheValueAndCache() {
            val cache = CacheAble("value") {
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
            val cache = CacheAble("value") {
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
            val cache = CacheAble("value") {
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
            val cache = CacheAble("value") {
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