package asl.pinblock

import java.util.Locale
import kotlin.random.Random

object SimpleIS0 {
// The most basic implemenation of ISO 9564-1 PIN block format 0 and 3

    // ISO-0 Encoding
    fun encodeIso0(pin: String, pan: String): String {
        val pinBlock = StringBuilder("0").append(pin.length).append(pin).append("FFFFFFFFFFFFFF".substring(pin.length))
        val panBlock = "0000" + pan.substring(pan.length - 13, pan.length - 1)

        return xor(pinBlock.toString(), panBlock)
    }

    // ISO-3 Encoding
    fun encodeIso3(pin: String, pan: String): String {
        val pinBlock = StringBuilder("3").append(pin.length).append(pin)
        for (i in pin.length until 14) {
            // Note documentation on eftlab is contradictory about fill digits -it mentions 10 to 15 but also X'0' to X'F'
            // ISO standard suggests 10 to 15 is the correct range
            pinBlock.append(Random.nextInt(10, 16).toString(16).uppercase(Locale.ROOT))
        }
        val panBlock = "0000" + pan.substring(pan.length - 13, pan.length - 1)

        return xor(pinBlock.toString(), panBlock)
    }

    // ISO-0 Decoding
    fun decodeIso0(pinBlock: String, pan: String): String {
        val panBlock = "0000" + pan.substring(pan.length - 13, pan.length - 1)
        val decodedPinBlock = xor(pinBlock, panBlock)
        val pinLength = decodedPinBlock[1].toString().toInt()
        return decodedPinBlock.substring(2, 2 + pinLength)
    }

    // ISO-3 Decoding
    fun decodeIso3(pinBlock: String, pan: String): String {
        val panBlock = "0000" + pan.substring(pan.length - 13, pan.length - 1)
        val decodedPinBlock = xor(pinBlock, panBlock)
        val pinLength = decodedPinBlock[1].toString().toInt()
        return decodedPinBlock.substring(2, 2 + pinLength)
    }

    fun xor(str1: String, str2: String): String {
        val result = StringBuilder()
        val length = minOf(str1.length, str2.length)  // Ensure we don't go out of bounds

        for (i in 0 until length) {
            val xorResult = Integer.parseInt(str1[i].toString(), 16) xor Integer.parseInt(str2[i].toString(), 16)
            result.append(Integer.toHexString(xorResult).uppercase(Locale.ROOT))
        }
        return result.toString()
    }
}