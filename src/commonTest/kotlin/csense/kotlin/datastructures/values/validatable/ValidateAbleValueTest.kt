package csense.kotlin.datastructures.values.validatable//package csense.kotlin.datastructures.values
//
//import csense.kotlin.tests.assertions.*
//import kotlin.test.*
//
//class ValidateAbleValueTest {
//
//    class Value() {
//        @Test
//        fun alwaysInvalidInvalidAllowed() {
//            val value: ValidateAbleValue<String> = ValidateAbleValue(
//                "",
//                { false },
//                disallowInvalidValues = false
//            )
//            value.value.assert("")
//            value.value = "542"
//            value.value.assert("542")
//        }
//
//        @Test
//        fun alwaysInvalidInvalidDisallowed() {
//            val value: ValidateAbleValue<String> = ValidateAbleValue(
//                "",
//                { false },
//                disallowInvalidValues = true
//            )
//            value.value.assert("")
//            value.value = "1234"
//            value.value.assert("", message = "should not change due to it being invalid")
//        }
//
//        @Test
//        fun alwaysValidInvalidAllowed() {
//            val value: ValidateAbleValue<String> = ValidateAbleValue(
//                "",
//                { true },
//                disallowInvalidValues = false
//            )
//            value.value.assert("")
//            value.value = "542"
//            value.value.assert("542")
//        }
//
//        @Test
//        fun alwaysValidInvalidDisallowed() {
//            val value: ValidateAbleValue<String> = ValidateAbleValue(
//                "",
//                { true },
//                disallowInvalidValues = true
//            )
//            value.value.assert("")
//            value.value = "abc"
//            value.value.assert("abc", message = "should be allowed since all are valid.")
//        }
//
//
//    }
//
//
//    @Test
//    fun isValueValid() {
//
//    }
//
//    @Test
//    fun isValueInvalid() {
//
//    }
//}