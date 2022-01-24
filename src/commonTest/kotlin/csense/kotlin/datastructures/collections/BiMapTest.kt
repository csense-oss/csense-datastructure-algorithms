package csense.kotlin.datastructures.collections

import csense.kotlin.tests.assertions.*
import kotlin.test.*

class BiMapTest {

}

class MutableBiMapImplTest {

    class Put {

        @Test
        fun empty() {
            val map = MutableBiMapImpl<String, String>()
            map.put("key", "value").assertFalse()
            map.size.assert(1)
            map.assertBothWays(key = "key", value = "value")
        }

        @Test
        fun noCollisions() {
            val map = MutableBiMapImpl<String, String>()
            map.put("1", "a").assertFalse()
            map.put("2", "b").assertFalse()
            map.size.assert(2)
            map.assertBothWays(key = "1", value = "a")
            map.assertBothWays(key = "2", value = "b")
        }

        @Test
        fun bothOverwritesDifferentRelations() {
            val map = MutableBiMapImpl<String, String>()
            map.put(key = "1", value = "a").assertFalse()
            map.put(key = "2", value = "b").assertFalse()
            map.put(key = "3", value = "c").assertFalse()

            map.put(key = "1", value = "c").assertTrue() //should remove value "a" and key "3"
            map.size.assert(2, message = "1 -> a should be removed as well as c -> 3")
            map.assertBothWays(key = "1", value = "c")
            map.assertBothWays(key = "2", value = "b")
        }

        @Test
        fun bothOverwritingEachOther() {
            val map = MutableBiMapImpl<String, String>()
            map.put("1", "a").assertFalse()

            map.put("1", "a").assertTrue()
            map.size.assert(1)
            map.assertBothWays(key = "1", value = "a")
        }

        @Test
        fun onlyValueOverwritesOldKey() {
            val map = MutableBiMapImpl<String, String>()
            map.put("1", "a").assertFalse()

            map.put("2", "a").assertTrue()
            map.size.assert(1)
            map.assertBothWays(key = "2", value = "a")
        }


        @Test
        fun onlyKeyOverwritesOldValue() {
            val map = MutableBiMapImpl<String, String>()
            map.put("1", "a").assertFalse()

            map.put("1", "b").assertTrue()
            map.size.assert(1)
            map.assertBothWays(key = "1", value = "b")
        }

    }


    @Test
    fun toBiMap() {
        val map = MutableBiMapImpl<String, String>()
        map.toBiMap().size.assert(0)


        val biMap = map.toBiMap()
        biMap.size.assert(0)
        map.put("key", "value")
        map.size.assert(1)
        biMap.size.assert(0, message = "should not be reflected in old results")
        map.toBiMap().apply {
            size.assert(1)
            assertBothWays("key", "value")
        }

    }


    class RemoveByKey {
        @Test
        fun empty() {
            val map = MutableBiMapImpl<String, String>()
            map.removeByKey("key").assertNull()
        }

        @Test
        fun notFound() {
            val map = MutableBiMapImpl<String, String>()
            map.put("key", "value")
            map.removeByKey("xxxx").assertNull()
            map.size.assert(1)
            map.assertBothWays("key", "value")
        }

        @Test
        fun found() {
            val map = MutableBiMapImpl<String, String>()
            map.put("key", "value")
            map.removeByKey("key").assertNotNullAndEquals("value")
            map.size.assert(0)
            map.keyMap.assertEmpty()
            map.valueMap.assertEmpty()
        }
    }

    class RemoveByValue {
        @Test
        fun empty() {
            val map = MutableBiMapImpl<String, String>()
            map.removeByValue("value").assertNull()
        }

        @Test
        fun notFound() {
            val map = MutableBiMapImpl<String, String>()
            map.put("key", "value")
            map.removeByValue("xxxx").assertNull()
            map.size.assert(1)
            map.assertBothWays("key", "value")
        }

        @Test
        fun found() {
            val map = MutableBiMapImpl<String, String>()
            map.put("key", "value")
            map.removeByValue("value").assertNotNullAndEquals("key")
            map.size.assert(0)
            map.keyMap.assertEmpty()
            map.valueMap.assertEmpty()
        }
    }
}


class DualMapBiMapTest {

    @Test
    fun size() {
        val empty = BiMapImmutableImpl<String, String>()
        empty.size.assert(0)

        val single = BiMapImmutableImpl<String, String>("1" to "a")
        single.size.assert(1)

        val multiple = BiMapImmutableImpl<String, String>("1" to "a", "2" to "b")
        multiple.size.assert(2)
    }


    class ToMap {
        @Test
        fun empty() {
            BiMapImmutableImpl<String, String>().toMap().assertEmpty()
        }

        @Test
        fun single() {
            BiMapImmutableImpl("1" to "a").toMap().assertSingle {
                it.key.assert("1")
                it.value.assert("a")
            }
        }

        @Test
        fun multiple() {
            TODO()
        }
    }
}

private fun <Key, Value> BiMap<Key, Value>.assertBothWays(key: Key, value: Value) {
    getKeyBy(value).assertNotNullAndEquals(key)
    getValueBy(key).assertNotNullAndEquals(value)
}
