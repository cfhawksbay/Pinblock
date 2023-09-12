package asl.pinblock

import java.util.Locale
import kotlin.random.Random

// Interface for ISO format

private const val ISO_CODE_0 = "0"
private const val ISO_CODE_3 = "3"
private const val MAX_FILL_DIGITS = 14
private const val MIN_RANDOM_FILL = 10
private const val MAX_RANDOM_FILL = 16
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
        return "0000" + pan
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
    override val isoCode = ISO_CODE_0

    override fun fillDigits(): String {
        return "FFFFFFFFFFFFFF".substring(pin.length)
    }
}

// Implementation of IsoFormat with isoCode 3
// NOTE: eftlab documentation is inconsistent as it both says that fill digits are random from 10 to 15 AND R is random value from X'0' to X'F'
// NOTE: I decided to implement fill digits as random from 10 to 15 as I found ISO documentation that says that fill digits are random from 10 to 15
class Iso3Format(override val pin: String, override val pan: String) : IsoFormat {
    override val isoCode = ISO_CODE_3

    override fun fillDigits(): String {
        val fillDigits = StringBuilder()
        for (i in pin.length until MAX_FILL_DIGITS) {
            fillDigits.append(Random.nextInt(MIN_RANDOM_FILL, MAX_RANDOM_FILL).toString(16).uppercase(Locale.ROOT))
        }
        return fillDigits.toString()
    }
}
