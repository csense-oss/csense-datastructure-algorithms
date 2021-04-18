package csense.kotlin.collections

import csense.kotlin.tests.assertions.assert
import csense.kotlin.tests.assertions.assertNotNullAndEquals
import csense.kotlin.tests.assertions.assertNotNullApply
import csense.kotlin.tests.assertions.assertNull
import org.junit.jupiter.api.Test

class ClassTypeMapTest {

    @Test
    fun get() {
        val map = ClassTypeMap<Container<*>>()
        map.get<Container<String>>().assertNull("nothing in map")
        map.put(Container("test"))
        map.get<Container<String>>().assertNotNullApply {
            this.item.assert("test")
        }
        map.get<Container<Int>>().assertNull()
    }

    @Test
    fun getClazz() {
        val map = ClassTypeMap<Container<*>>()
        val clazz = Container::class.java
        map.get(clazz).assertNull("nothing in map")
        map.put(Container("test"))
        map.get(clazz).assertNotNullApply {
            this.item.assertNotNullAndEquals("test")
        }

    }

    @Test
    fun putValue() {
        val map = ClassTypeMap<Container<*>>()
        map.get<Container<String>>().assertNull("nothing in map")
        map.put(Container("test"))
        map.get<Container<String>>().assertNotNullAndEquals("test")
    }

    @Test
    fun putClazz() {
        val map = ClassTypeMap<Container<*>>()
        map.get<Container<String>>().assertNull("nothing in map")
        map.put(Container("test"))
        map.get<Container<String>>().assertNotNullAndEquals("test")
    }
}

private class Container<T>(val item: T)