package com.signalbridge.app.domain.validation

import com.signalbridge.app.domain.model.InvalidNumber
import com.signalbridge.app.domain.model.ForwardingCondition
import com.signalbridge.app.domain.model.ForwardingOperationResult

/**
 * Normalizes and validates destination phone numbers.
 *
 * Validation proves format plausibility only. It never proves reachability,
 * ownership, or that the carrier will accept the number.
 */
object NumberValidator {

    /** Bangladesh country code. */
    const val BD_COUNTRY_CODE = "880"

    /** Default international access prefix. */
    const val INTERNATIONAL_PREFIX = "+"

    /**
     * Normalize a user-entered number into E.164-ish form.
     * - Strips spaces, dashes, parentheses, dots.
     * - Converts a leading "00" to "+".
     * - Ensures a leading "+" for international numbers.
     * - Assumes Bangladesh (880) for a local 10-digit mobile number when no
     *   country code is present.
     */
    fun normalize(raw: String?): String? {
        if (raw.isNullOrBlank()) return null
        var s = raw.trim()
        // Remove common formatting characters.
        s = s.replace(Regex("[\\s\\-\\(\\)\\.]+"), "")
        if (s.isBlank()) return null
        if (s.startsWith("00")) {
            s = "+" + s.removePrefix("00")
        }
        if (!s.startsWith("+")) {
            // No country code present: treat as Bangladesh local number.
            val digits = s.filter { it.isDigit() }
            if (digits.length == 10 && digits.first() == '0') {
                // Local mobile like 01XXXXXXXXX -> +8801XXXXXXXXX
                return "+" + BD_COUNTRY_CODE + digits.removePrefix("0")
            }
            if (digits.isNotEmpty()) {
                return "+" + digits
            }
        }
        return s
    }

    val BD_MOBILE_PREFIX = Regex("^\\+8801[3-9][0-9]{8}$")

    /**
     * Validate a normalized number. Returns null when valid, or an
     * [InvalidNumber] result describing why it is not.
     *
     * A syntactically valid number is NOT confirmed reachable.
     */
    fun validate(normalized: String?): InvalidNumber? {
        val number = normalized ?: return InvalidNumber(
            operationId = "", condition = ForwardingCondition.UNCONDITIONAL,
            timestampMs = 0, number = "", reasonKey = "error_number_empty"
        )
        if (!number.startsWith("+")) {
            return InvalidNumber(
                operationId = "", condition = ForwardingCondition.UNCONDITIONAL,
                timestampMs = 0, number = number, reasonKey = "error_number_missing_country_code"
            )
        }
        val digits = number.substring(1)
        if (!digits.all { it.isDigit() }) {
            return InvalidNumber(
                operationId = "", condition = ForwardingCondition.UNCONDITIONAL,
                timestampMs = 0, number = number, reasonKey = "error_number_invalid_characters"
            )
        }
        when {
            BD_MOBILE_PREFIX.matches(number) -> return null // valid BD mobile
            number.length >= 7 && number.length <= 16 -> return null // plausible international
            else -> return InvalidNumber(
                operationId = "", condition = ForwardingCondition.UNCONDITIONAL,
                timestampMs = 0, number = number, reasonKey = "error_number_invalid_length"
            )
        }
    }

    /** Normalize then validate. */
    fun validateRaw(raw: String?): Pair<String?, InvalidNumber?> {
        val normalized = normalize(raw)
        return normalized to validate(normalized)
    }

    /** Mask a full number for display/history/notifications. */
    fun mask(number: String?): String {
        return number.takeIf { it.isNotBlank() }?.run {
            val digits = filter { it.isDigit() }
            if (digits.length <= 4) digits else "****" + digits.takeLast(4)
        } ?: ""
    }
}
