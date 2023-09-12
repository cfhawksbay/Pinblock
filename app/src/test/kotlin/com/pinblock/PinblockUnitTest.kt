package com.pinblock

import asl.pinblock.Pan
import asl.pinblock.Pin
import asl.pinblock.Iso0Format
import asl.pinblock.PinBlock
import asl.pinblock.api.PinBlockISO0
import asl.pinblock.api.PinBlockISO3
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.jupiter.api.assertThrows

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PinblockUnitTest {
    //https://www.eftlab.com/knowledge-base/complete-list-of-pin-blocks

    companion object {
        private val pan = Pan("43219876543210987")
        private val pin = Pin("1234")
    }


    @Test
    fun pinblockIso0Test() {
        val pinBlock = PinBlock(::Iso0Format, pin, pan)
        assertEquals(pinBlock.encode(), "0412AC89ABCDEF67")
    }

    @Test
    fun pinblockISO0DecodeTest() {
        val pin = Pin("1234")
        val pan = Pan("1111222233334444")
        val pinBlock = PinBlock(::Iso0Format, pin, pan)
        val clearPinBlock = pinBlock.encode()
        assertEquals(pinBlock.decode(clearPinBlock, pan), pin.toString())
    }

    @Test
    fun `PIN with less than 4 digits should throw IllegalArgumentException`() {
        assertThrows<IllegalArgumentException> {
            Pin("123")
        }
    }

    @Test
    fun `PIN with more than 12 digits should throw IllegalArgumentException`() {
        assertThrows<IllegalArgumentException> {
            Pin("1234567890123")
        }
    }

    @Test
    fun `PIN with non-numeric characters should throw IllegalArgumentException`() {
        assertThrows<IllegalArgumentException> {
            Pin("123A")
        }
    }

    @org.junit.jupiter.api.Test
    fun `valid PAN should initialize successfully`() {
        val pan = Pan("1234567890123")
        assertNotNull(pan)
    }

    @org.junit.jupiter.api.Test
    fun `PAN with less than 13 digits should throw IllegalArgumentException`() {
        assertThrows<IllegalArgumentException> {
            Pan("123456789012")
        }
    }

    @org.junit.jupiter.api.Test
    fun `PAN with non-numeric characters should throw IllegalArgumentException`() {
        assertThrows<IllegalArgumentException> {
            Pan("12345678901A3")
        }
    }

    @org.junit.jupiter.api.Test
    fun `takeLast12 should return last 12 digits excluding the check digit`() {
        val pan = Pan("12345678901234")
        assertEquals("234567890123", pan.takeLast12())
    }

    // API Tests
    @Test
    fun `use api ISO0 class`() {
        val pinBlock = PinBlockISO0("1234", "43219876543210987")
        assertEquals(pinBlock.encode(), "0412AC89ABCDEF67")
    }
    @Test
    fun `use api ISO3 class`() {
        val pin = "1234"
        val pan = "1111222233334444"
        val pinBlock = PinBlockISO3(pin, pan)
        val clearPinBlock = pinBlock.encode()
        assertEquals(pinBlock.decode(clearPinBlock, pan), pin)
    }
}
