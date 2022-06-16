package csense.kotlin.datastructures.values.state

import csense.kotlin.*
import csense.kotlin.annotations.collections.*
import csense.kotlin.annotations.numbers.*

public class CircularStates<T>(
    @CollectionSizeAtLeast(1)
    private val states: List<T>
) {

    @IntLimit(from = 0)
    private var statePointer = 0

    public val value: T
        get() = states[statePointer]

    public fun next() {
        nextStatePointer()
    }

    public fun previous() {
        previousStatePointer()
    }

    private fun previousStatePointer() {
        statePointer = statePointer.dec().floorModToSize()
    }

    private fun nextStatePointer() {
        statePointer = statePointer.inc().floorModToSize()
    }

    private fun Int.floorModToSize(): Int {
        return floorMod(states.size)
    }

}
