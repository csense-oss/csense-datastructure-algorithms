@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package csense.kotlin.collections

/**
 * A Container for types where you have a Class<T> and some matching type (eg a Generic structure with a Type argument of T)
 *
 * @param Type The type of value(s)
 */
@DisallowTypeErasedGeneric
public class ClassTypeMap<in Type> {

    private val map = mutableMapOf<Class<*>, Type>()

    public val size: Int
        get() = map.size


    public inline fun <reified T> get(): T? where T : Type =
        get(T::class.java)

    @Suppress("UNCHECKED_CAST")
    public fun <T> get(clazz: Class<T>): T? where T : Type =
        map[clazz] as? T

    public inline fun <reified T> put(value: T): T? where T : Type =
        put(T::class.java, value)

    @Suppress("UNCHECKED_CAST")
    public fun <T> put(clazz: Class<T>, value: T): T? where T : Type =
        map.put(clazz, value) as? T


    public inline fun <reified T> remove(): T? where T : Type =
        remove(T::class.java)

    @Suppress("UNCHECKED_CAST")
    public fun <T> remove(clazz: Class<T>): T? where T : Type {
        return map.remove(clazz) as? T
    }

    public fun clear(): Unit = map.clear()


    public fun isEmpty(): Boolean =
        size == 0

    public fun isNotEmpty(): Boolean =
        !isEmpty()


    public inline fun <reified T> contains(): Boolean where T : Type =
        contains(T::class.java)

    public fun <T> contains(clazz: Class<T>): Boolean where T : Type =
        map.containsKey(clazz)

    public inline fun <reified T> containsNot(): Boolean where T : Type =
        containsNot(T::class.java)

    public fun <T> containsNot(clazz: Class<T>): Boolean where T : Type =
        !contains(clazz)

}

//TODO better name
/**
 * Purpose:
 * Disallowing the following example
 * class Container<T>()
 * val map = Map<Class<T>, String>
 * map.get(Container<String>::class.java) //will become Container<object> at runtime, type erasure...
 *
 */
public annotation class DisallowTypeErasedGeneric {

}