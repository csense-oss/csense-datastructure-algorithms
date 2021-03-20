package csense.kotlin.datastructures.caching

public class ValueWithCacheComputation<Value, CachedType>(
    private val initialValue: Value,
    private val calculateCache: (Value) -> CachedType
) {

    private var _cachedValue: CachedType? = null
    public val cachedValue: CachedType
        get() = _cachedValue ?: calculateCache(value).also {
            _cachedValue = it
        }

    public var value: Value = initialValue
        set(newValue) {
            if (newValue != value) {
                field = newValue
                invalidateCache()
            }
        }

    private fun invalidateCache() {
        _cachedValue = null
    }

    public fun resetToInitial() {
        value = initialValue
    }
}