package csense.kotlin.datastructures.caching

public class ValueWithCacheComputation<Value, CachedType>(
    private val initialValue: Value,
    private val calculateCache: (Value) -> CachedType
) {
    private var _cachedValue: CachedComputableValue<CachedType> = CachedComputableValue {
        calculateCache(value)
    }

    /**
     * The cached value (based on the value)
     * If this has not been computed it will be on retrieval
     */
    public val cachedValue: CachedType by _cachedValue

    /**
     * The stored value
     * getting has no side effect
     * setting has the side effect of invaliding the current cached value
     */
    public var value: Value = initialValue
        set(newValue) {
            if (newValue == value) {
                return
            }
            field = newValue
            _cachedValue.invalidate()
        }

    /**
     * Resets the value to the initial value (this also invalidates the cached value)
     */
    public fun resetToInitial() {
        value = initialValue
    }
}

