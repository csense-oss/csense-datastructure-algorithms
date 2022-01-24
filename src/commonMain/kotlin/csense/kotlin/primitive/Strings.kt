package csense.kotlin.primitive


public sealed class Strings(public val string: String) {
    public object Empty : Strings("")

    public class Blank internal constructor(blankString: String) : Strings(blankString) {
        public companion object {
            public val space: Blank = Blank(" ")
        }
    }

    public class Content internal constructor(contentString: String) : Strings(contentString)


    public companion object {
        public fun from(string: String): Strings {
            return when {
                string.isEmpty() -> Empty
                string.isBlank() -> Blank(string)
                else -> Content(string)
            }
        }
    }

    public fun isEmptyOrBlank(): Boolean =
        this == Empty || this is Blank
}