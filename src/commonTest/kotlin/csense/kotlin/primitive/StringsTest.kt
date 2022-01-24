package csense.kotlin.primitive

import csense.kotlin.tests.assertions.*
import kotlin.test.*

class StringsTest {
    class From {
        @Test
        fun empty() {
            Strings.from("").assertIs<Strings.Empty>()
        }

        @Test
        fun blank() {
            Strings.from(" ").apply {
                assertIs<Strings.Blank>()
                this.string.assert(" ")
            }
            Strings.from("\n").apply {
                assertIs<Strings.Blank>()
                this.string.assert("\n")
            }
            Strings.from("\r\n").apply {
                assertIs<Strings.Blank>()
                this.string.assert("\r\n")
            }
            Strings.from("\t").apply {
                assertIs<Strings.Blank>()
                this.string.assert("\t")
            }
        }

        @Test
        fun content() {
            Strings.from("a").apply {
                assertIs<Strings.Content>()
                this.string.assert("a")
            }
            Strings.from(" a").apply {
                assertIs<Strings.Content>()
                this.string.assert(" a")
            }
            Strings.from(" a ").apply {
                assertIs<Strings.Content>()
                this.string.assert(" a ")
            }
            Strings.from("1").apply {
                assertIs<Strings.Content>()
                this.string.assert("1")
            }
        }

    }


    @Test
    fun isEmptyOrBlank() {
        Strings.Empty.isEmptyOrBlank().assertTrue()
        Strings.Blank.space.isEmptyOrBlank().assertTrue()
        Strings.from("a").isEmptyOrBlank().assertFalse()
    }
}