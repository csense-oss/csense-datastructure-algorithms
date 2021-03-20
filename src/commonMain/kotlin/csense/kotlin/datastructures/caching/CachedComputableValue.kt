package csense.kotlin.datastructures.caching

import csense.kotlin.Function0R
import kotlin.reflect.KProperty

public class CachedComputableValue<Value>(
    private val computeFunction: Function0R<Value>
) {

    private var cachedValue: Value? = null

    /**
     * The current (cached) value or the [computeFunction] will be executed and returned (and saved)
     * @TimeComplexity O(computeFunction)
     */
    public val value: Value
        get() = cachedValue ?: computeFunction().also {
            cachedValue = it
        }

    /**
     * Clears the cached value, implying that the next get [value] will call [computeFunction]
     * @TimeComplexity O(1)
     */
    public fun invalidate() {
        cachedValue = null
    }

    /**
     * Makes this usable as a delegated property by the "value"
     * @param thisRef Any?
     * @param property KProperty<*>
     * @return Value
     * @TimeComplexity O(value)
     */
    public operator fun getValue(thisRef: Any?, property: KProperty<*>): Value =
        value
}