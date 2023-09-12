package asl.pinblock

class PinBlock(isoFactory: (String, String) -> IsoFormat, pin: Pin, pan: Pan) {
    val isoFormat = isoFactory(pin.toString(), pan.takeLast12())

    fun encode(): String {
        return isoFormat.encode() // double dispatch
    }

    fun decode(pinBlock: String, pan: Pan): String {
        return isoFormat.decode(pinBlock, pan.takeLast12())
    }
}


