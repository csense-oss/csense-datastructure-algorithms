@file:Suppress("unused", "NOTHING_TO_INLINE", "MemberVisibilityCanBePrivate")

package csense.kotlin.algorithms

import csense.kotlin.annotations.numbers.IntLimit
import csense.kotlin.annotations.numbers.LongLimit
import csense.kotlin.datastructures.caching.ValueWithCacheComputation

/**
 *
 * The base class for handling calculating the average, with the ability to add more values to it
 * /akk the running average.
 *
 */
public abstract class RunningAverageAbstract<T : Number> {

    /**
     *
     */
    @LongLimit(from = 0)
    private var numberCount: Long = 0

    /**
     *
     */
    private val aggregatedValue: ValueWithCacheComputation<T, Double> by lazy {
        ValueWithCacheComputation(zero) {
            it.toDouble() / numberCount
        }
    }

    /**
     * The neutral element
     */
    protected abstract val zero: T

    /**
     *
     * @param first T
     * @param second T
     * @return T
     */
    protected abstract fun addValues(first: T, second: T): T

    /**
     *
     * @param newValue T
     */
    public fun addValue(newValue: T) {
        aggregatedValue.update(addValues(aggregatedValue.getValue(), newValue))
        numberCount += 1
    }

    /**
     *
     */
    public val average: Double
        get() = aggregatedValue.getCachedValue()

    /**
     *
     */
    public fun reset() {
        aggregatedValue.reset()
        numberCount = 0
    }
}


/**
 *
 */
public open class RunningAverageInt : RunningAverageAbstract<Int>() {
    override fun addValues(first: Int, second: Int): Int = first + second
    override val zero: Int = 0
}

/**
 *
 */
public open class RunningAverageDouble : RunningAverageAbstract<Double>() {
    override fun addValues(first: Double, second: Double): Double = first + second
    override val zero: Double = 0.0
}

/**
 *
 */
public open class RunningAverageFloat : RunningAverageAbstract<Float>() {
    override fun addValues(first: Float, second: Float): Float = first + second
    override val zero: Float = 0f
}

/**
 *
 * @param T : [Number]
 * @property cappedNumberOfValues [Int]
 * @property valuesSet [Int]
 * @property currentIndex [Int]
 * @property average [Double]
 * @constructor
 */
public abstract class RunningAverageCappedAbstract<T : Number>(
    @IntLimit(from = 0) private val cappedNumberOfValues: Int
) {

    /**
     *
     */
    public abstract fun clearValues()

    /**
     *
     * @param item T
     * @param index [Int]
     */
    public abstract fun setValue(item: T, @IntLimit(from = 0) index: Int)

    /**
     *
     * @param toTakeCount [Int]
     * @return Iterable<T>
     */
    public abstract fun takeValues(@IntLimit(from = 0) toTakeCount: Int): Iterable<T>

    /**
     * How many values we have set of the available elements in the array
     */
    @Suppress("InheritanceInitializationOrder")
    private val valuesSet: ValueWithCacheComputation<Int, Double> = ValueWithCacheComputation(
        initialValue = 0
    ) { valuesSet: Int ->
        takeValues(valuesSet).sumByDouble(Number::toDouble) / valuesSet
    }

    /**
     * Since the array acts as a ring buffer.
     */
    @IntLimit(from = 0)
    private var currentIndex = 0

    /**
     *
     * @param newValue T
     */
    public fun addValue(newValue: T) {
        valuesSet.update(minOf(valuesSet.getValue() + 1, cappedNumberOfValues))
        setValue(newValue, currentIndex)
        currentIndex = (currentIndex + 1).rem(cappedNumberOfValues)
    }

    /**
     *
     */
    public val average: Double
        get() = valuesSet.getCachedValue()

    /**
     *
     */
    public fun reset() {
        valuesSet.reset()
        currentIndex = 0
        clearValues()
    }

}

/**
 *
 * @property values [FloatArray]
 * @constructor
 */
public open class RunningAverageFloatCapped(
    cappedValuesToAverage: Int
) : RunningAverageCappedAbstract<Float>(cappedValuesToAverage) {

    private val values = FloatArray(cappedValuesToAverage)

    override fun takeValues(
        @IntLimit(from = 0) toTakeCount: Int
    ): List<Float> = values.take(toTakeCount)

    override fun setValue(item: Float, @IntLimit(from = 0) index: Int) {
        values[index] = item
    }

    override fun clearValues(): Unit = values.fill(0f)
}

/**
 *
 * @property values [IntArray]
 * @constructor
 */
public open class RunningAverageIntCapped(cappedValuesToAverage: Int) :
    RunningAverageCappedAbstract<Int>(cappedValuesToAverage) {

    private val values = IntArray(cappedValuesToAverage)

    override fun clearValues(): Unit = values.fill(0)

    override fun setValue(item: Int, @IntLimit(from = 0) index: Int) {
        values[index] = item
    }

    override fun takeValues(
        @IntLimit(from = 0) toTakeCount: Int
    ): Iterable<Int> = values.take(toTakeCount)
}

/**
 *
 * @property values [DoubleArray]
 * @constructor
 */
public open class RunningAverageDoubleCapped(
    cappedValuesToAverage: Int
) : RunningAverageCappedAbstract<Double>(cappedValuesToAverage) {

    private val values = DoubleArray(cappedValuesToAverage)

    override fun clearValues(): Unit = values.fill(0.0)


    override fun setValue(item: Double, @IntLimit(from = 0) index: Int) {
        values[index] = item
    }


    override fun takeValues(@IntLimit(from = 0) toTakeCount: Int): Iterable<Double> =
        values.take(toTakeCount)

}
