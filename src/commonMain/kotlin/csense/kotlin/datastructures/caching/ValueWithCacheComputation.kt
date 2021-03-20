package csense.kotlin.datastructures.caching

public class ValueWithCacheComputation<Value, CachedType>(
    private val initialValue: Value,
    private val calculateCache: (Value) -> CachedType
) {
    private var cachedValue: CachedType? = null

    private var currentValue: Value = initialValue
        set(value) {
            field = value
            invalidateCache()
        }

    private fun invalidateCache() {
        cachedValue = null
    }


    public fun update(newValue: Value) {
        if (newValue != currentValue) {
            currentValue = newValue
        }
    }

    public fun getCachedValue(): CachedType {
        return cachedValue ?: calculateCache(currentValue).also {
            cachedValue = it
        }
    }


    public fun getValue(): Value = currentValue
    public fun reset() {
        currentValue = initialValue
    }
}