package csense.kotlin.datastructures.values.state

import csense.kotlin.datastructures.values.state.swapable.*
import csense.kotlin.tests.assertions.*
import kotlin.test.*

class SwapBooleanTest {

    @Test
    fun trueStart() {
        val swapBoolean = SwapBoolean(initialValue = true)
        swapBoolean.value.assertTrue()
        swapBoolean.swap()
        swapBoolean.value.assertFalse()
    }

    @Test
    fun falseStart() {
        val swapBoolean = SwapBoolean(initialValue = false)
        swapBoolean.value.assertFalse()
        swapBoolean.swap()
        swapBoolean.value.assertTrue()
    }

}