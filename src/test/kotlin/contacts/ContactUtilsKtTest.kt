package contacts

import com.javapda.contacts.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ContactUtilsKtTest {

    @Test
    fun testContactListJsonGenerator() {
        println(ContactListJsonGenerator().toJson(personsAndOrganizations()))
        val contacts: List<Contact> =
            ContactListJsonGenerator().fromJson(ContactListJsonGenerator().toJson(personsAndOrganizations()))
        require(contacts.isNotEmpty())
    }

    @Test
    fun testContactJsonGenerator() {
        println(ContactJsonGenerator().toJson(persons()[0]))
        val contact: Contact = ContactJsonGenerator().fromJson(ContactJsonGenerator().toJson(persons()[0]))
        require(contact is Person)
    }

    @Test
    fun testPersonListJsonGenerator() {
        println(PersonListJsonGenerator().toJson(persons()))
    }

    @Test
    fun testOrganizationListJsonGenerator() {
        println(OrganizationListJsonGenerator().toJson(organizations()))
    }

    /**
     * Test person json
     * Test ability to serialize (toJson) and deserialize (fromJson) a Person
     */
    @ParameterizedTest
    @MethodSource("persons")
    fun testPersonJson(person: Person) {
        println(person.toJson(person))
        println(person.fromJson(person.toJson(person)))
    }


    @ParameterizedTest
    @MethodSource("organizations")
    fun testOrganizationJson(organization: Organization) {
        println(organization.toJson(organization))
        println(organization.fromJson(organization.toJson(organization)))
    }

    @Test
    fun testLocalDateTimeFormat() {
        assertEquals("2024-07-15T07:51", formatLocalDateTime(LocalDateTime.parse("2024-07-15T07:51:55.452555900")))
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

    companion object {
        @JvmStatic
        fun organizations(): List<Organization> {
            return listOf(
                Organization(
                    name = "Trump Tower",
                    address = "725 5th Ave., New York, NY 10022",
                    phoneNumber = "(212) 832-2000"
                ),
                Organization(
                    name = "White House",
                    address = "1600 Pennsylvania Ave., Washington, DC 20500",
                    phoneNumber = "(202) 456-1111"
                ),
            )
        }

        @JvmStatic
        fun persons(): List<Person> {
            return listOf(
                Person(
                    name = "Nikola",
                    surname = "Tesla",
                    gender = "M",
                    birthDate = LocalDateTime.of(1856, 6, 28, 0, 0).toString(),
                    phoneNumber = "+381 xx xxx xx xx" // Serbia
                ),
                Person(
                    name = "Marie",
                    surname = "Curie",
                    gender = "F",
                    birthDate = LocalDateTime.of(1867, 11, 7, 0, 0).toString(),
                    phoneNumber = "+48 xx xxx xx xx"  // Poland
                ),

                )
        }

        @JvmStatic
        fun personsAndOrganizations(): List<Contact> {
            val list = mutableListOf<Contact>()
            list.addAll(persons())
            list.addAll(organizations())
            return list
        }
    }
}