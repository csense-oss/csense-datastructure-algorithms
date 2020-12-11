# Csense kotlin - Datastructures and algorithms

This library contains various datastructures and algorithms that are not "common" enough to be in the csense-kotlin
library.

## Showcases

### Running Average


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

