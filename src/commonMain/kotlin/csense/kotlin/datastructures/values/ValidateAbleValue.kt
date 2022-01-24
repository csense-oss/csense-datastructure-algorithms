@file:Suppress("MemberVisibilityCanBePrivate")

package csense.kotlin.datastructures.values

import csense.kotlin.*

public typealias OnInvalidValueCallback<T> = (invalidValue: T) -> Unit

public open class ValidateAbleValue<T>(
    initialValue: T,
    validator: Function1<T, Boolean>,
    disallowInvalidValues: Boolean,
    onSetInvalidValue: OnInvalidValueCallback<T>? = null,
    onSetValidValue: Function0<T>? = null
) : ValidateAbleValueWithReason<T, Unit>(
    initialValue = initialValue,
    validator = {
        if (validator(it)) {
            ValidateAbleValueWithReasonResult.Valid
        } else {
            ValidateAbleValueWithReasonResult.FailedWithReason(Unit)
        }
    },
    disallowInvalidValues = disallowInvalidValues,
    onSetInvalidValue = { invalidValue, _ ->
        onSetInvalidValue?.invoke(invalidValue)
    },
    onSetValidValue = onSetValidValue
) {
}