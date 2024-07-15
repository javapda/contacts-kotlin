package contacts

import com.javapda.contacts.Contact
import com.javapda.contacts.Person
import org.junit.jupiter.api.Test

class ContactTest {
    @Test
    fun testPerson() {
        val person = Person(name = "John", surname = "Smith", gender = "M", phoneNumber =  "(520) 333-2344")
        println(person)
//        Contact(name = "John", surname = "Smith", phoneNumber =  "(520) 333-2344")
    }
}