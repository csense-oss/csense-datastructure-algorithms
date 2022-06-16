package csense.kotlin.algorithms

import csense.kotlin.tests.assertions.*
import kotlin.test.*

class RunningAverageTest {


}

class RunningAverageIntTestTest {

    class AddValues {

        @Test
        fun canAddAndCalculateAverage() {
            val runningAverage = RunningAverageInt()
            runningAverage.average.assert(value = Double.NaN) // message = "nothing means there are no average"

            runningAverage.addValue(10)
            runningAverage.average.assert(value = 10.0, delta = 0.1)


            runningAverage.addValue(30)
            runningAverage.average.assert(value = 20.0, delta = 0.1, "average of 30 + 10 is 20")

        }

    }
}