package csense.kotlin.datastructures.values

import csense.kotlin.tests.assertions.*
import kotlin.test.*

class LockableValueTest {
    class Value {
        
        @Test
        fun testValue() {
            val lockableValue = LockableValue(1, "")
            lockableValue.value.assert("")
            lockableValue.value = "test"
            lockableValue.value.assert("test")
            lockableValue.value = "test2"
            lockableValue.value.assert("test", "should not allow updating more times than specified")
        }
        
        @Test
        fun testValueZeroCount() {
            val lockableValue = LockableValue(0, "")
            lockableValue.value.assert("")
            lockableValue.value = "test"
            lockableValue.value.assert("")
        }
    }

    @Test
    fun lockableValueTIsLocked() {
        //TODO make me.

    }

    @Test
    fun lockableValueTIsUnlocked() {
        //TODO make me.

    }

    @Test
    fun lock() {
        //TODO make me.

    }

    @Test
    fun lockWith() {
        //TODO make me.

    }
}