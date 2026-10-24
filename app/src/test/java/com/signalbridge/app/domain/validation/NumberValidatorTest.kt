package com.signalbridge.app.domain.validation

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import com.signalbridge.app.domain.model.InvalidNumber
import com.signalbridge.app.domain.model.ForwardingCondition

class NumberValidatorTest {

    @Test
    fun `normalizes bangladesh local mobile to e164`() {
        assertEquals("+8801712345678", NumberValidator.normalize("01712345678"))
        assertEquals("+8801712345678", NumberValidator.normalize("0171-2345678"))
        assertEquals("+8801712345678", NumberValidator.normalize("+8801712345678"))
        assertEquals("+8801712345678", NumberValidator.normalize("008801712345678"))
    }

    @Test
    fun `rejects empty input`() {
        assertNull(NumberValidator.normalize(""))
        assertNull(NumberValidator.normalize("   "))
        assertNull(NumberValidator.normalize(null))
    }

    @Test
    fun `validates valid bangladesh mobile`() {
        assertNull(NumberValidator.validate("+8801712345678"))
    }

    @Test
    fun `rejects missing plus`() {
        val invalid = NumberValidator.validate("8801712345678")
        assertNotNull(invalid)
        assertTrue(invalid is InvalidNumber)
    }

    @Test
    fun `rejects invalid characters`() {
        val invalid = NumberValidator.validate("+88017abcdefgh")
        assertNotNull(invalid)
    }

    @Test
    fun `masks full number for display`() {
        assertEquals("****5678", NumberValidator.mask("+8801712345678"))
        assertEquals("1234", NumberValidator.mask("1234"))
        assertEquals("", NumberValidator.mask(null))
    }

    @Test
    fun `validateRaw returns normalized and invalid pair`() {
        val (normalized, invalid) = NumberValidator.validateRaw("01712345678")
        assertEquals("+8801712345678", normalized)
        assertNull(invalid)
    }

    @Test
    fun `international numbers plausible`() {
        assertNull(NumberValidator.validate("+14155552671"))
        assertNull(NumberValidator.validate("+442079460000"))
    }
}
