package csense.kotlin.datastructures.collections

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
 * @property order [ArrayList]<Key>
 * @constructor
 */
public class SimpleLRUCache<Key, Value>(
        @IntLimit(from = 1)
        private var cacheSize: Int
) {
    
    init {
        //make sure no one is evil enough to try and set an invalid cache size.
        cacheSize = getLeastValidCacheSize(cacheSize)
    }
    
    //TODO consider linked hashmap, potentially own edition where we can query the order since this is a "boring" replica of that.
    private val map = HashMap<Key, Value>(cacheSize)
    
    private val order = arrayListOf<Key>()
    
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
        order.add(key)
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
        return map[key]
    }
    
    /**
     * Gets a given value , and if there and the given condition is met the value is returned,
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
        return if (order.isNotEmpty()) {
            order.removeAt(0)
        } else {
            null
        }
    }
    
    /**
     *
     * @param key Key
     * @TimeComplexity O(n) where n = size of data
     */
    public fun remove(key: Key) {
        map.remove(key)
        order.remove(key)
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
        order.clear()
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