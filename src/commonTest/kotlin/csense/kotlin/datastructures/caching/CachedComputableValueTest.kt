package csense.kotlin.datastructures.caching

import csense.kotlin.tests.assertions.assert
import csense.kotlin.tests.assertions.assertCalled
import kotlin.test.Test

class CachedComputableValueTest {

    @Test
    fun value() = assertCalled { shouldBeCalled ->
        val cache = CachedComputableValue {
            shouldBeCalled()
            42
        }
        cache.value.assert(42)
    }

    @Test
    fun getValue() = assertCalled(times = 1) { shouldBeCalled ->
        val x by CachedComputableValue {
            shouldBeCalled()
            "test"
        }
        x.assert("test")
        x.assert("test", message = "should not change(nor should it recompute)")
    }

    @Test
    fun invalidate() = assertCalled(times = 2) { shouldBeCalled ->
        var counter = 0
        val cache = CachedComputableValue {
            shouldBeCalled()
            counter += 1
            "$counter"
        }
        cache.value.assert("1")
        cache.value.assert("1")
        cache.invalidate()
        cache.value.assert("2", message = "should update cache on invalidates")
    }


    /**
     * This tricky tests verifies that iff the user uses this with method references (which works wrt to initialization order)
     * then accessing variables etc will behave as expected, even if they "otherwise" would violate the initialization order.
     */
    @Test
    fun initializationOrderShouldBeRespected() {
        MyBadInitializationOrderClass().myCache.assert("42+42")
    }
}

class MyBadInitializationOrderClass {
    val myCache: String by CachedComputableValue(::computeExpensiveString)

    private fun computeExpensiveString(): String {
        return "$canPotentialFailWith0+42"
    }

    private val canPotentialFailWith0 = 42
}