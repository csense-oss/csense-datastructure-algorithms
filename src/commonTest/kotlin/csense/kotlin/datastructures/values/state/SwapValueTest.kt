package csense.kotlin.datastructures.values.state

import csense.kotlin.tests.assertions.*
import kotlin.test.*

class SwapValueTest {
    @Test
    fun swap() {
        val mySwapValue = SwapValue<String>(
            "start",
            "end"
        )
        mySwapValue.value.assert("start")
        mySwapValue.swap()
        mySwapValue.value.assert("end")
        mySwapValue.swap()
        mySwapValue.value.assert("start")
    }

    @Test
    fun value() {
        val mySwapValue = SwapValue<String>(
            "start",
            "end"
        )
        mySwapValue.value.assert("start")
    }
}