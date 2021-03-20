# Csense kotlin - Datastructures and algorithms

This library contains various datastructures and algorithms that are not "common" enough to be in the csense-kotlin
library.

## Showcases

### SetOnceBool

A very normal pattern is to set a value once, especially booleans

#### Before

```kotlin
var x = false
fun doSomething() {
    if (x) {
        return
    }
    x = true
    //more code here
}
```

#### After

```kotlin
val x = SetOnceBool(false)
fun doSomething() = x.lockWithAction {

}
```

### CachedComputableValue

A very normal pattern is to compute a value lazy but with the ability to invalidate the cached value

#### Before

```kotlin
class MyClass {
    private var myInnerCache: String? = null

    val myCache: String
        get() = myInnerCache ?: computeExpensiveString().also {
            myInnerCache = it
        }

    private fun computeExpensiveString(): String {
        return "42+42"
    }

    fun invalidate() {
        myInnerCache = null
    }
}
```

#### After

```kotlin
class MyClass {
    private val myInnerCache = CachedComputableValue {
        "42+42"
    }
    val myCache: String by myInnerCache

    fun invalidate() {
        myInnerCache.invalidate()
    }
}
```

### Running Average

#### Before

#### After

