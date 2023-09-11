package asl.pinblock

class PinBlock(isoFactory: (String, String) -> IsoFormat, pin: String, pan: String) {
    val isoFormat = isoFactory(pin, pan)

    fun encode(): String {
        return isoFormat.encode()
    }

    fun decode(pinBlock: String, pan: String): String {
        return isoFormat.decode(pinBlock, pan)
    }
}


