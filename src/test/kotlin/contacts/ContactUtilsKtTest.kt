package contacts

import com.javapda.contacts.isValidPhoneNumber
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ContactUtilsKtTest {

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
    @ValueSource(strings = ["(212)867-5309","+0(123)456-789-9999","()()"])
    fun testIsInvalidPhoneNumber(phoneNumber: String) {
        assertFalse(isValidPhoneNumber(phoneNumber))
    }
}