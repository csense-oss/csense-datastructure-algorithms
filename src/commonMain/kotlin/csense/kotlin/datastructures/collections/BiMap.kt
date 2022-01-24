@file:Suppress("NOTHING_TO_INLINE")

package csense.kotlin.datastructures.collections

import csense.kotlin.extensions.*
import csense.kotlin.extensions.collections.map.*

public interface BiMap<Key, Value> {
    public val size: Int

    public fun toMap(): Map<Key, Value>

    public fun containsKey(key: Key): Boolean
    public fun containsValue(value: Value): Boolean

    public fun getValueBy(key: Key): Value?
    public fun getKeyBy(value: Value): Key?

}

internal interface DualMapBiMap<Key, Value> : BiMap<Key, Value> {
    val keyMap: Map<Key, Value>
    val valueMap: Map<Value, Key>

    //TODO hmm?
    @Suppress("InheritanceInitializationOrder")
    override val size: Int
        get() = keyMap.size

    override fun toMap(): Map<Key, Value> {
        return keyMap
    }

    override fun containsKey(key: Key): Boolean {
        return keyMap.containsKey(key)
    }

    override fun containsValue(value: Value): Boolean {
        return valueMap.containsKey(value)
    }

    override fun getValueBy(key: Key): Value? {
        return keyMap[key]
    }

    override fun getKeyBy(value: Value): Key? {
        return valueMap[value]
    }
}

public interface MutableBiMap<Key, Value> : BiMap<Key, Value> {
    public fun put(key: Key, value: Value): Boolean
    public fun removeByKey(key: Key): Value?
    public fun removeByValue(value: Value): Key?
    public fun toBiMap(): BiMap<Key, Value>

}

internal class BiMapImmutableImpl<Key, Value> : DualMapBiMap<Key, Value> {
    constructor(fromMap: Map<Key, Value>) {
        keyMap = fromMap.toMap()
        valueMap = fromMap.reverse()
    }

    constructor(fromPairs: List<Pair<Key, Value>>) {
        keyMap = fromPairs.toMap()
        valueMap = keyMap.reverse()
    }

    constructor(vararg fromPairs: Pair<Key, Value>) {
        keyMap = fromPairs.toMap()
        valueMap = keyMap.reverse()
    }

    override val keyMap: Map<Key, Value>

    override val valueMap: Map<Value, Key>

}

internal class MutableBiMapImpl<Key, Value> : MutableBiMap<Key, Value>, DualMapBiMap<Key, Value> {

    override val keyMap: MutableMap<Key, Value> = hashMapOf()

    override val valueMap: MutableMap<Value, Key> = hashMapOf()

    /**
     *
     * @param key Key
     * @param value Value
     * @return Boolean true if any overwriting / collisions occurred, false otherwise
     */
    override fun put(key: Key, value: Value): Boolean {


        @Suppress("MissingTestFunction") //TODO bug in plugin
        fun removeOldCollisions(): Boolean {
            val oldKeyForValue: Key? = valueMap[value]
            val oldValueForKey: Value? = keyMap[key]
            //the only optimization we can do is to avoid removing if the put relation already exists
            if (oldKeyForValue == key && oldValueForKey == value) {
                return true
            }
            return keyMap.remove(oldKeyForValue).isNotNull || valueMap.remove(oldValueForKey).isNotNull
        }

        val haveAndCollisions = removeOldCollisions()
        keyMap[key] = value
        valueMap[value] = key
        return haveAndCollisions

    }

    override fun toBiMap(): BiMap<Key, Value> {
        return BiMapImmutableImpl(keyMap)
    }

    override fun removeByKey(key: Key): Value? {
        return keyMap.remove(key)?.also(valueMap::remove)
    }

    override fun removeByValue(value: Value): Key? {
        return valueMap.remove(value)?.also(keyMap::remove)
    }

}


public fun <Key, Value> biMapOf(vararg pairs: Pair<Key, Value>): BiMap<Key, Value> {
    return BiMapImmutableImpl(*pairs)
}

public fun <Key, Value> Map<Key, Value>.toBiMap(): BiMap<Key, Value> {
    return BiMapImmutableImpl(this)
}

public fun <Key, Value> Map<Key, Value>.toMutableBiMap(): MutableBiMap<Key, Value> {
    return MutableBiMapImpl<Key, Value>().also { it ->
        it.putAll(this)
    }
}

public fun <Key, Value> MutableBiMap<Key, Value>.put(
    pair: Pair<Key, Value>
) {
    put(pair.first, pair.second)
}

public fun <Key, Value> MutableBiMap<Key, Value>.put(
    entry: Map.Entry<Key, Value>
) {
    put(entry.key, entry.value)
}

public fun <Key, Value> MutableBiMap<Key, Value>.putAll(
    map: Map<Key, Value>
) {
    map.forEach(::put)
}

public fun <Key, Value> MutableBiMap<Key, Value>.putAll(
    pairs: Iterable<Pair<Key, Value>>
) {
    pairs.forEach(::put)
}

public fun <Key, Value> MutableBiMap<Key, Value>.putAllEntries(
    entries: Iterable<Map.Entry<Key, Value>>
) {
    entries.forEach(::put)
}

public fun <Key, Value> MutableBiMap<Key, Value>.removeByKeys(
    keys: Iterable<Key>
): List<Value?> {
    return keys.map(this::removeByKey)
}

public fun <Key, Value> MutableBiMap<Key, Value>.removeByValue(
    values: Iterable<Value>
): List<Key?> {
    return values.map(this::removeByValue)
}