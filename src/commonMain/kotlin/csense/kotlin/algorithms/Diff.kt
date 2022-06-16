package csense.kotlin.algorithms

import csense.kotlin.extensions.collections.map.*


public inline fun <Key, Value> Map<Key, Value>.computeDiff(
    other: Map<Key, Value>,
    crossinline areValuesEqual: (key: Key, fromValue: Value, toValue: Value) -> Boolean
): DiffResultMap<Key, Value> {
    
    val uniqueKeysInFirst = HashMap<Key, Value>()
    val sameContent = HashMap<Key, Value>()
    val changedContent = HashMap<Key, Pair<Value, Value>>()

    this.forEach {
        val otherValue = other[it.key]
        if (otherValue == null) {
            uniqueKeysInFirst.put(it)
        } else {
            if (areValuesEqual(it.key, it.value, otherValue)) {
                sameContent.put(it)
            } else {
                changedContent[it.key] = Pair(it.value, otherValue)
            }
        }
    }

    val uniqueKeysInSecond = other.filter {
        this.doesNotContainKey(it.key)
    }

    return DiffResultMap(
        uniqueKeysInFirst = uniqueKeysInFirst,
        uniqueKeysInSecond = uniqueKeysInSecond,
        sameContent = sameContent,
        changedContent = changedContent
    )

}

public data class DiffResultMap<Key, Value>(
    val uniqueKeysInFirst: Map<Key, Value>,
    val uniqueKeysInSecond: Map<Key, Value>,
    val sameContent: Map<Key, Value>,
    val changedContent: Map<Key, Pair<Value, Value>>
)