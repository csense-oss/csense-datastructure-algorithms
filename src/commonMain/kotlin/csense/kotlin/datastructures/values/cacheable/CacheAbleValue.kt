//package csense.kotlin.datastructures.values
//
//import kotlinx.coroutines.*
//
///**
// * Something that can be cached and evicted
// */
//public class CacheAbleValue<Value> {
//
//    private var cache: Value? = null
//    public fun evict() {
//        cache = null
//    }
//
//    public fun getOrCacheBy(retrieveValue: () -> Value): Value {
//        return cache ?: retrieveValue().updateCache()
//    }
//
//    public fun getOrDefault(defaultValue: Value): Value {
//        return cache ?: defaultValue
//    }
//
//    private fun Value.updateCache(): Value = apply {
//        cache = this
//    }
//
//}