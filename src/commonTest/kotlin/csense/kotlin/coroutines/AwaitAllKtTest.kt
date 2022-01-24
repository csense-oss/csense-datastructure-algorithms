@file:OptIn(ExperimentalCoroutinesApi::class)

package csense.kotlin.coroutines

import csense.kotlin.tests.assertions.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import kotlin.test.*

class AwaitAllKtTest {

    @Test
    fun awaitAllFirstSecond() = runTest {
        val arg1 = async { "" }
        val arg2 = async { 42 }
        val (stringRes, intRes) = awaitAll(arg1, arg2)
        stringRes.assert("")
        intRes.assert(42)
    }

    @Test
    fun awaitAllFirstSecondThird() = runTest {
        val arg1 = async { "a" }
        val arg2 = async { 42 }
        val arg3 = async { listOf<String>() }
        val (stringRes, intRes, listRes) = awaitAll(arg1, arg2, arg3)
        stringRes.assert("a")
        intRes.assert(42)
        listRes.assertEmpty()
    }
}