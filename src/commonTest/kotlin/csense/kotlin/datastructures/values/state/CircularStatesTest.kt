package csense.kotlin.datastructures.values.state

import csense.kotlin.datastructures.values.state.circular.*
import csense.kotlin.tests.assertions.*
import kotlin.test.*

class CircularStatesTest {

    class Value {
        @Test
        fun single() {
            val states = CircularStates(
                listOf("test")
            )
            states.value.assert("test")
            states.next()
            states.value.assert("test")
            states.previous()
            states.value.assert("test")
        }

        @Test
        fun multiple() {
            val states = CircularStates(
                listOf("test", "1234", "qwerty")
            )
            states.value.assert("test")
            states.next()
            states.value.assert("1234")

            states.previous()
            states.value.assert("test")
        }
    }


    class Next {
        @Test
        fun single() {
            val states = CircularStates(
                listOf("test")
            )
            states.value.assert("test")
            states.next()
            states.value.assert("test")
        }

        @Test
        fun multiple() {
            val states = CircularStates(
                listOf("test", "1234", "qwerty")
            )
            states.value.assert("test")
            states.next()

            states.value.assert("1234")
            states.next()

            states.value.assert("qwerty")

            states.next()
            states.value.assert("test")

            states.next()
            states.value.assert("1234")
        }

    }


    class Previous {
        @Test
        fun single() {
            val states = CircularStates(
                listOf("test")
            )
            states.value.assert("test")
            states.next()
            states.value.assert("test")
        }

        @Test
        fun multiple() {
            val states = CircularStates(
                listOf("test", "1234", "qwerty")
            )
            states.value.assert("test")
            states.previous()

            states.value.assert("qwerty")
            states.previous()

            states.value.assert("1234")
            states.previous()

            states.value.assert("test")
        }
    }
}