package asl.pinblock

import java.util.Locale
import kotlin.random.Random

// Interface for ISO format
interface IsoFormat {
    val isoCode: String
    val pin: String
    val pan: String

    fun encode(): String {
        val pinBlock = pinblock()
        val panBlock = panblock()
        return xor(pinBlock, panBlock)
    }

    fun decode(pinBlock: String, pan: String): String {
        val panBlock = panblock()
        val decodedPinBlock = xor(pinBlock, panBlock)
        val pinLength = decodedPinBlock[1].toString().toInt()
        return decodedPinBlock.substring(2, 2 + pinLength)
    }

    fun pinblock(): String {
        val pinBlock = StringBuilder(isoCode)
            .append(pin.length)
            .append(pin)
            .append(fillDigits())
        return pinBlock.toString()
    }

    fun fillDigits(): String

    fun panblock(): String {
        return "0000" + pan.substring(pan.length - 13, pan.length - 1)
    }

    fun xor(str1: String, str2: String): String {
        val result = StringBuilder()
        val length = minOf(str1.length, str2.length)
        for (i in 0 until length) {
            val xorResult = str1[i].toString().toInt(16) xor str2[i].toString().toInt(16)
            result.append(xorResult.toString(16).uppercase(Locale.ROOT))
        }
        return result.toString()
    }
}

// Implementation of IsoFormat with isoCode 0
class Iso0Format(override val pin: String, override val pan: String) : IsoFormat {
    override val isoCode = "0"

    override fun fillDigits(): String {
        return "FFFFFFFFFFFFFF".substring(pin.length)
    }
}

// Implementation of IsoFormat with isoCode 3
class Iso3Format(override val pin: String, override val pan: String) : IsoFormat {
    override val isoCode = "3"

    override fun fillDigits(): String {
        val fillDigits = StringBuilder()
        for (i in pin.length until 14) {
            fillDigits.append(Random.nextInt(10, 16).toString(16).uppercase(Locale.ROOT))
        }
        return fillDigits.toString()
    }
}
