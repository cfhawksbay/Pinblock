package asl

data class Pan(private val value: String) {
    init {
        require(value.length > 12) { "PAN must be at leat 13 digits" }
        require(value.all { it.isDigit() }) { "PAN must be numeric" }
    }

    fun takeLast12(): String = value.takeLast(13).dropLast(1) //last 12 digits excluding the check digit
}