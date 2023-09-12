package asl.pinblock

data class Pin(private val value: String) {
    companion object {
        const val MIN_PIN_LENGTH = 4
        const val MAX_PIN_LENGTH = 12
    }

    init {
        require(value.length in MIN_PIN_LENGTH..MAX_PIN_LENGTH) { "PIN must be between $MIN_PIN_LENGTH and $MAX_PIN_LENGTH digits" }
        require(value.all { it.isDigit() }) { "PIN must be numeric" }
    }

    override fun toString() = value
}
