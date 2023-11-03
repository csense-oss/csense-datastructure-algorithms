@file:Suppress("unused", "INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package csense.kotlin.datastructures.collections.lru

import kotlin.internal.*
import csense.kotlin.*
import csense.kotlin.annotations.numbers.*
import csense.kotlin.extensions.*
import csense.kotlin.extensions.primitives.*
import kotlin.Function2
import kotlin.also


/**
 * Simple LRU cache with good time complexity but a somewhat "higher" memory usage. (time over memory)
 * @param Key
 * @param Value
 * @property cacheSize [Int]
 * @property map [HashMap]<Key, Value>
 * @constructor
 */
public class LRUCache<Key, Value>(
    @IntLimit(from = 1)
    private var cacheSize: Int
) {

    init {
        cacheSize = getLeastValidCacheSize(cacheSize)
    }

    private val map = LinkedHashMap<Key, Value>(cacheSize)

    /**
     *
     * @param key Key
     * @param value Value
     * @return Key?
     * @TimeComplexity O(1)
     */
    public fun put(key: Key, value: Value): Key? {
        val evictedKey: Key? = shouldEvict().mapLazy(
            ifTrue = { evict() },
            ifFalse = { null })

        map[key] = value
        return evictedKey
    }

    /**
     *
     * @param key Key
     * @return [Boolean]
     * @TimeComplexity O(1)
     */
    public fun containsKey(key: Key?): Boolean {
        if (key == null) {
            return false
        }
        return map.containsKey(key)
    }

    /**
     *
     * @param key Key
     * @return [Boolean]
     * @TimeComplexity O(1)
     */
    public fun notContainsKey(key: Key?): Boolean = !containsKey(key)

    /**
     *
     * @return Boolean true if we are at max size thus we have to evict.
     */
    private fun shouldEvict(): Boolean = map.size >= cacheSize

    /**
     *
     * @return Key?
     * @TimeComplexity O(1)
     */
    private fun evict(): Key? = getKeyToEvict().also {
        map.remove(it)
    }

    /**
     *
     * @param key Key
     * @return Value?
     * @TimeComplexity O(1)
     */
    public operator fun get(key: Key?): Value? {
        if (key == null) {
            return null
        }
        val value = map[key] ?: return null
        map.remove(key)
        map[key] = value
        return value
    }

    private fun moveToBack(key: Key, value: Value) {
        map.moveToBack(key)
        map.remove(key)
        map[key] = value
    }

    /**
     * Gets a given value , and if it is there and the given condition is met the value is returned,
     * if the condition is not met, the item is evicted and null is returned.
     * @return value?
     * @TimeComplexity O(n)
     */
    public fun getOrRemove(key: Key, condition: Function2<Key, Value, Boolean>): Value? {
        val value: Value = get(key) ?: return null
        return if (condition(key, value)) {
            value
        } else {
            remove(key)
            null
        }
    }

    /**
     *
     * @return Key?
     * @TimeComplexity O(1)
     */
    private fun getKeyToEvict(): Key? {
        return map.keys.firstOrNull()?.apply {
            map.remove(this)
        }
    }

    /**
     *
     * @param key Key
     * @TimeComplexity O(n) where n = size of data
     */
    public fun remove(key: Key): Value? {
        return map.remove(key)
    }


    /**
     * Allows you to change the size of this LRU cache
     * if the size is lower, the "oldest" entry(/ies) will be purged.
     * @param newSize [Int] the new size to use.
     * @TimeComplexity O(n)
     */
    public fun setCacheSize(@IntLimit(from = 1) newSize: Int) {
        if (newSize < cacheSize) {
            removeOldest(cacheSize - newSize)
        }
        cacheSize = getLeastValidCacheSize(newSize)
    }

    /**
     * Removes the first count elements
     * @param count [Int]
     * @TimeComplexity O(n)
     */
    public fun removeOldest(@IntLimit(from = 1) count: Int): Unit = count.forEach {
        getKeyToEvict()?.let(this::remove)
    }

    /**
     *
     * @param key Key
     * @param value [Function0R]<Value>
     * @return Value
     */
    public fun getOrPut(key: Key, value: Function0R<Value>): Value {
        val haveKey = get(key)
        return if (haveKey != null) {
            haveKey
        } else {
            val computedValue = value()
            put(key, computedValue)
            computedValue
        }
    }

    /**
     *
     * @param key Key
     * @param value Value
     * @return Key?
     * @TimeComplexity O(1)
     */
    public operator fun set(key: Key, value: Value): Key? = put(key, value)


    /**
     * Clears all data.
     * @TimeComplexity O(1)
     */
    public fun clear() {
        map.clear()
    }

    /**
     * Get the least valid cache size, so take unsafe input and turns it safe (sane)
     * @param size [Int]
     * @return [Int] a safe value for this cache size least valued.
     * @TimeComplexity O(1)
     */
    private fun getLeastValidCacheSize(@IntLimit(from = 1) size: Int): Int {
        return size.coerceAtLeast(1)
    }
}

public inline fun <Key, Value> MutableMap<Key, Value>.moveToBack(key: Key) {
    val removed: Value = remove(key) ?: return
    put(key, removed)
}

public inline fun <Key, Value> LRUCache<Key, Value>.getOrPut(key: Key, value: Value): Value {
    return getOrPut(key = key, getValue = { value })
}

@LowPriorityInOverloadResolution
public inline fun <Key, Value> LRUCache<Key, Value>.getOrPut(
    key: Key,
    getValue: () -> Value
): Value {
    val contains: Value? = get(key)
    if (contains != null) {
        return contains
    }
    val value: Value = getValue()
    put(key, value)
    return value
}



