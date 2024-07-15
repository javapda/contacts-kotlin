package contacts

import com.javapda.contacts.isValidPhoneNumber
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertTrue

//fun ivpn(phoneNumber: String): Boolean {
////    return """[+]?[\da-zA-Z0-9]?\s*()""".toRegex().matches(phoneNumber)
////    return phoneNumber.matches(Regex("^(?:\\+.\\s\\d{2})|(?:\\+\\(\\S{5,7}\\))|^(?:\\d{1,3}\$)|^(?:\\(\\d{1,3}\\)\$)|^(?:\\d{3}.[a-zA-Z]{3})|^(?:\\d{3}.[0-9]{3}.[a-zA-Z]{3})|^(?:\\d{3}.[0-9]{3}.[0-9]{3}\$)|^(?:\\d{3}.\\(\\d{3}\\))\$|^(?:\\d{3}.\\d{2}.[a-z]{2,4}.[0-9]{2})|^(?:\\d{3}.\\(\\d{3}\\).[0-9]{3}\$)|^(?:\\d{3}.\\(\\d{2}\\).\\d{2}.\\d{2})|^(?:\\(\\d{3}\\).\\d{3}\$)|^(?:\\(\\d{3}\\).\\d{3}.\\d{3})"))
////    return phoneNumber.matches(Regex("^(\\+.\\s\\d{2})|(\\+\\(\\S{5,7}\\))|^(\\d{1,3}\$)|^(\\(\\d{1,3}\\)\$)|^(\\d{3}.[a-zA-Z]{3})|^(\\d{3}.[0-9]{3}.[a-zA-Z]{3})|^(\\d{3}.[0-9]{3}.[0-9]{3}\$)|^(\\d{3}.\\(\\d{3}\\))\$|^(\\d{3}.\\d{2}.[a-z]{2,4}.[0-9]{2})|^(\\d{3}.\\(\\d{3}\\).[0-9]{3}\$)|^(\\d{3}.\\(\\d{2}\\).\\d{2}.\\d{2})|^(\\(\\d{3}\\).\\d{3}\$)|^(\\(\\d{3}\\).\\d{3}.\\d{3})"))
//
//    return phoneNumber.matches(Regex("""[+]?(\d*) \(123\) 456-789-ABcd"""))
//}

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactsTest {


    @Test
    fun bregex() {
        require("""[+]?0 \(123\) 456-789-ABcd""".toRegex().matches("+0 (123) 456-789-ABcd"))
        require("""[+]?0 \(123\) 456-789-ABcd""".toRegex().matches("0 (123) 456-789-ABcd"))
        require("""^[+]?0 \(123\) 456-789-ABcd$""".toRegex().matches("0 (123) 456-789-ABcd"))
        require("""^[+]?(0) \(123\) 456-789-ABcd$""".toRegex().matches("0 (123) 456-789-ABcd"))
        require("""^[+]?(\d+) \(\d+\) 456-789-ABcd$""".toRegex().matches("0 (123) 456-789-ABcd"))
        require("""^[+]?([a-zA-Z0-9\- ]+)\(\d+\)([a-zA-Z0-9\- ]+)$""".toRegex().matches("0 (123) 456-789-ABcd"))

        require(
            """^[+]?([a-zA-Z0-9\- ]+)\([a-zA-Z0-9\- ]+\)([a-zA-Z0-9\- ]+)$""".toRegex().matches("0 (123) 456-789-ABcd")
        )
        require(
            """^[+]?([a-zA-Z0-9\- ]+)*(\([a-zA-Z0-9\- ]+\))([a-zA-Z0-9\- ]+)$""".toRegex()
                .matches("0 (123) 456-789-ABcd")
        )
        val regexText = """^[+]?([a-zA-Z0-9\- ]+)*(\([a-zA-Z0-9\- ]+\))([a-zA-Z0-9\- ]+)$|^[a-zA-Z0-9\- ]+$"""
        require("""^[+]?([a-zA-Z0-9\- ]+)*(\([a-zA-Z0-9\- ]+\))([a-zA-Z0-9\- ]+)$""".toRegex().matches("(123) 345-456"))
        require(regexText.toRegex().matches("(123) 345-456"))
        require(regexText.toRegex().matches("123 456 xyz"))
        val firstNumWithBrackets = """"""
        val secondNumWithBrackets = """"""
        val noBracketsAtAll = """"""
        // "\\+?(($firstNumWithBrackets)|($secondNumWithBrackets)|($noBracketsAtAll))"
        val tomaszPajakRegex =
            """^(?:\+\(\S{5,7}\)|\+\d{2}|\d{1,3}$|\(\d{1,3}\)|\d{3}(?:[-. ]\w{3}){0,2}$|\d{3}\(\d{3}\)$|\d{3}\(\d{2}\)\d{2}\.\d{2}|\(\d{3}\)\d{3}(?:[-. ]\d{3}){0,2})$"""
    }

//    //    @ParameterizedTest
//    @ValueSource(
//        strings = ["+0 (123) 456-789-ABcd", "0 (123) 456-789-ABcd", "(123) 345-456", "(123) 234 345-456",
//            "123 456 xyz"]
//    )
////    @ValueSource(strings = ["+","+0"])
//    fun validPhoneNumber(phoneNumber: String) {
//        assertTrue(isValidPhoneNumber(phoneNumber))
//    }

//    @ParameterizedTest
//    @ValueSource(strings = ["+0(123)456-789-9999"])
//    fun invalidPhoneNumber(phoneNumber: String) {
//        assertFalse(isValidPhoneNumber(phoneNumber))
//    }


}
