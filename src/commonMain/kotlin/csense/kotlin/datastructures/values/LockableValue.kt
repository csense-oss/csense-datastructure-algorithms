package csense.kotlin.datastructures.values

import csense.kotlin.*
import csense.kotlin.annotations.numbers.IntLimit
import csense.kotlin.extensions.primitives.isNegativeOrZero
import kotlin.reflect.*

/**
 * A mutable container that locks the amount of updates that are allowed
 * @param T the type of value we are to contain
 * @property maxUpdateCount [Int] the amount of updates that are allowed
 * @property value T the starting value that "may" be updated up to the provided update count
 */
public open class LockableValue<T>(
        @IntLimit(from = 1) maxUpdateCount: Int,
        initialValue: T,
        private val onUpdateRejected: EmptyFunction? = null
) {
    @IntLimit(from = 0)
    private var remainingUpdateCount = maxUpdateCount
    
    /**
     * The current value (getter)
     * setting the value might not update it if the maxUpdateCount reaches zero or negative
     */
    public var value: T = initialValue
        set(newValue) {
            if (remainingUpdateCount.isNegativeOrZero) {
                onUpdateRejected?.invoke()
                return
            }
            remainingUpdateCount -= 1
            field = newValue
        }
}

/**
 *
 * @receiver LockableValue<T>
 * @param any Any?
 * @param property KProperty<*>
 * @return T
 */
public operator fun <T> LockableValue<T>.getValue(any: Any?, property: KProperty<*>): T {
    return value
}

/**
 *
 * @receiver LockableValue<T>
 * @param item Any?
 * @param property KProperty<*>
 * @param newValue T
 */
public operator fun <T> LockableValue<T>.setValue(item: Any?, property: KProperty<*>, newValue: T) {
    value = newValue
}
