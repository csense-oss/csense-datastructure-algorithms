package csense.kotlin.datastructures.values.state

public class SwapBoolean(
    initialValue: Boolean
) : SwapValue<Boolean>(
    initial = initialValue,
    secondaryState = !initialValue
)