package contacts

import com.javapda.contacts.Contact
import org.junit.jupiter.api.Test

class ContactTest {
    @Test
    fun basic() {
        Contact(name = "John", surname = "Smith", phoneNumber =  "(520) 333-2344")
    }
}