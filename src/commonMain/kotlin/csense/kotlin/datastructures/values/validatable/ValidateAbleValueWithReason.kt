package csense.kotlin.datastructures.values.validatable//@file:Suppress("MemberVisibilityCanBePrivate")
//@file:OptIn(ExperimentalContracts::class)
//
//package csense.kotlin.datastructures.values
//
//import csense.kotlin.*
//import kotlin.contracts.*
//
//public typealias OnInvalidValueWithReasonCallback<T, Reason> = (
//    invalidValue: T,
//    reason: Reason
//) -> Unit
//
//public sealed class ValidateAbleValueWithReasonResult<out Reason> {
//
//    public object Valid : ValidateAbleValueWithReasonResult<Nothing>()
//
//    public class FailedWithReason<Reason>(
//        public val reason: Reason
//    ) : ValidateAbleValueWithReasonResult<Reason>()
//}
//
//public open class ValidateAbleValueWithReason<T, Reason>(
//    initialValue: T,
//    validator: Function1<T, ValidateAbleValueWithReasonResult<Reason>>,
//    private val disallowInvalidValues: Boolean,
//    private val onSetInvalidValue: OnInvalidValueWithReasonCallback<T, Reason>? = null,
//    private val onSetValidValue: Function0<T>? = null
//) {
//
//    private val validation = validator
//
//    public var value: T = initialValue
//        set(newValue) {
//            val validationResult = validation(newValue)
//            if (validationResult.isInvalid()) {
//                onSetInvalidValue?.invoke(newValue, validationResult.reason)
//                if (disallowInvalidValues) {
//                    return
//                }
//            } else {
//                onSetValidValue?.invoke(newValue)
//            }
//            field = newValue
//        }
//
//    public fun isValueValid(): Boolean =
//        validation(value) == ValidateAbleValueWithReasonResult.Valid
//
//    public fun isValueInvalid(): Boolean = !isValueValid()
//
//    public fun getInvalidReasonOrNull(): Reason? {
//        val asFailed = validation(value) as? ValidateAbleValueWithReasonResult.FailedWithReason ?: return null
//        return asFailed.reason
//    }
//
//}
//
//public fun <Reason> ValidateAbleValueWithReasonResult<Reason>.isInvalid(): Boolean {
//    contract {
//        returns(true) implies (this@isInvalid is ValidateAbleValueWithReasonResult.FailedWithReason)
//    }
//    return this is ValidateAbleValueWithReasonResult.FailedWithReason<Reason>
//}
//
//public fun <Reason> ValidateAbleValueWithReasonResult<Reason>.isValid(): Boolean {
//    contract {
//        returns(true) implies (this@isValid is ValidateAbleValueWithReasonResult.Valid)
//    }
//    return this is ValidateAbleValueWithReasonResult.Valid
//}