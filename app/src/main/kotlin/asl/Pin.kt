package asl

data class Pin(private val value: String) {
    init {
        require(value.length in 4..12) { "PIN must be between 4 and 12 digits" }
        require(value.all { it.isDigit() }) { "PIN must be numeric" }
    }

    override fun toString() = value
}