package asl.pinblock.api

import asl.pinblock.Pan
import asl.pinblock.Pin
import asl.pinblock.Iso0Format
import asl.pinblock.Iso3Format
import asl.pinblock.PinBlock

interface IPinBlock {
    val pinBlock: PinBlock

    fun encode(): String {
        return pinBlock.encode()
    }

    fun decode(pinBlockString: String, pan: String): String {
        return pinBlock.decode(pinBlockString, Pan(pan))
    }
}

class PinBlockISO0(pin: String, pan: String) : IPinBlock {
    override val pinBlock = PinBlock(::Iso0Format, Pin(pin), Pan(pan))
}

class PinBlockISO3(pin: String, pan: String) : IPinBlock {
    override val pinBlock = PinBlock(::Iso3Format, Pin(pin), Pan(pan))
}