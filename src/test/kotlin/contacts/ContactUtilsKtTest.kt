package contacts

import com.javapda.contacts.formatLocalDateTime
import com.javapda.contacts.isValidGender
import com.javapda.contacts.isValidLocalDateText
import com.javapda.contacts.isValidPhoneNumber
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ContactUtilsKtTest {

    @Test
    fun testLocalDateTimeFormat() {
        assertTrue(true)
        assertEquals("2024-07-15T07:51",formatLocalDateTime(LocalDateTime.parse("2024-07-15T07:51:55.452555900")))
    }

    @ParameterizedTest
    @ValueSource(strings = ["M", "F"])
    fun testIsValidGender(gender: String) {
        assertTrue(isValidGender(gender))
    }

    @ParameterizedTest
    @ValueSource(strings = ["m", "f", "", "Male", "Female"])
    fun testIsInvalidGender(gender: String) {
        assertFalse(isValidGender(gender))
    }

    @ParameterizedTest
    @ValueSource(strings = ["2024-01-23"])
    fun testIsValidLocalDateText(dateAsText: String) {
        assertTrue(isValidLocalDateText(dateAsText))
    }

    @ParameterizedTest
    @ValueSource(strings = ["BOGUS"])
    fun testIsInvalidLocalDateText(dateAsText: String) {
        assertFalse(isValidLocalDateText(dateAsText))
    }

    @ParameterizedTest
    @ValueSource(
        strings = ["123"]
//                strings = ["+0 (123) 456-789-ABcd", "0 (123) 456-789-ABcd",
//            "(123) 345-456", "(123) 234 345-456", "123 456 xyz", "123"]
    )
    fun testIsValidPhoneNumber(phoneNumber: String) {
        assertTrue(isValidPhoneNumber(phoneNumber))
    }


    @ParameterizedTest
    @ValueSource(strings = ["(212)867-5309", "+0(123)456-789-9999", "()()"])
    fun testIsInvalidPhoneNumber(phoneNumber: String) {
        assertFalse(isValidPhoneNumber(phoneNumber))
    }
}