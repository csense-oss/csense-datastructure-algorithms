package csense.kotlin.datastructures.values.state

import csense.kotlin.datastructures.values.state.swapable.*
import csense.kotlin.tests.assertions.*
import kotlin.test.*

class SwappableTest {
    @Test
    fun swapAndGetValue() {
        val swapValue = SwapValue(
            initial = "start",
            secondaryState = "end"
        )
        swapValue.value.assert("start")

        swapValue.swapAndGetValue().assert("end")
        swapValue.value.assert("end")

        swapValue.swapAndGetValue().assert("start")
        swapValue.value.assert("start")

    }
}