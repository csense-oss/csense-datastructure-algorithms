package csense.kotlin.datastructures.values.state.swapable

public class SwapBoolean(
    initialValue: Boolean
) : SwapValue<Boolean>(
    initial = initialValue,
    secondaryState = !initialValue
)