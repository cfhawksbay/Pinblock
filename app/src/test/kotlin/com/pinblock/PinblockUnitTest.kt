package com.pinblock

import asl.pinblock.Iso0Format
import asl.pinblock.PinBlock
import asl.pinblock.SimpleIS0.decodeIso0
import asl.pinblock.SimpleIS0.encodeIso0
import asl.pinblock.SimpleIS0.encodeIso3
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PinblockUnitTest {

    //https://www.eftlab.com/knowledge-base/complete-list-of-pin-blocks
    @Test
    fun eftlabISO0EncodeTest() {
        val pin = "1234"
        val pan = "43219876543210987"

        assertEquals(encodeIso0(pin, pan), "0412AC89ABCDEF67")
    }

    @Test
    fun eftlabISO0DecodeTest() {
        val pin = "1234"
        val pan = "43219876543210987"
        val clearPinBlock = encodeIso0(pin, pan)

        assertEquals(decodeIso0(clearPinBlock, pan), pin)
    }

    @Test
    fun eftlabISO3EncodeDecodeTest() {
        val pin = "1234"
        val pan = "43219876543210987"
        val clearPinBlock = encodeIso3(pin, pan)

        assertEquals(decodeIso0(clearPinBlock, pan), pin)
    }

    @Test
    fun pinblockIso0Test() {
        val pin = "1234"
        val pan = "43219876543210987"
        val pinBlock = PinBlock(::Iso0Format, pin, pan)
        assertEquals(pinBlock.encode(), "0412AC89ABCDEF67")
    }

    @Test
    fun pinblockISO0DecodeTest() {
        val pin = "1234"
        val pan = "43219876543210987"
        val pinBlock = PinBlock(::Iso0Format, pin, pan)
        val clearPinBlock = pinBlock.encode()
        assertEquals(pinBlock.decode(clearPinBlock, pan), pin)
    }
}
