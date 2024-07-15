package com.javapda.contacts

/**
 * Contacts
 *
 * HINT Stage 2/4:
 *
 * This stage is not about the regexp. I mean, it's not the most essential part of the task...
 *
 * @constructor Create empty Contacts
 */

class Contacts(preload: Boolean = false) {
    enum class Stage {
        MAIN_MENU, ADD_CONTACT, EDIT_CONTACT, REMOVE_CONTACT
    }


    private val contacts = mutableListOf<Contact>()
    private var stage = Stage.MAIN_MENU

    init {
        if (preload) {
            contacts.addAll(
                listOf<Contact>(
                    Person("John", "Smith", "", "", "(212) 234-4321"),
                    Person("Ben", "Franklin", "1706-01-17", "M", "(212) 998-2651"),
                    Organization("Tesla", "123 Main St.", "(212) 998-2651"),
                )
            )
        }
    }

    private fun promptUser(prompt: String, blanksOkay: Boolean = false): String {
        print(prompt)
        var line: String = ""
        var promptCount = 0
        while (line == "" && (!blanksOkay || promptCount == 0)) {
            promptCount++
            line = readln()
        }
        return line
    }

    fun stage2of4() {
        // implemented in the main loop
        // this included adding a main menu
    }

    fun stage1of4() {

        val name = promptUser("Enter the name of the person:")
        val surname = promptUser("Enter the surname of the person:")
        val phoneNumber = promptUser("Enter the number:")
        println(
            """
            A record created!
            A Phone Book with a single record created!
        """.trimIndent()
        )

    }

    fun userInput(userInput: String): Boolean {
        if (userInput == "" && stage == Stage.MAIN_MENU) {
            mainPrompt()
            return true
        } else if (userInput == "exit") {
            return false
        }

        when (userInput) {
            "add" -> addContact()
            "remove" -> removeContact()
            "edit" -> editContact()
            "count" -> countContacts()
            "info" -> infoContacts()
            "list" -> listContacts(true)
            else -> mainPrompt()
        }
        return true
    }

    private fun listContactsForInfo() {
        contacts.forEachIndexed { idx, contact ->
            if (contact is Person) {
                println("${idx + 1}. ${contact.name} ${contact.surname}")
            } else if (contact is Organization) {
                println("${idx + 1}. ${contact.name}")
            }
        }
    }

    private fun infoContacts() {
        listContactsForInfo()
        val choice = promptUser("Enter index to show info: > ").toInt()
        require(choice in 1..contacts.size) { "choice should be 1 to ${contacts.size}, but found '$choice'" }
        when (val contact = contacts[choice - 1]) {
            is Person -> {
                val person = contact as Person
                with(person) {
                    println(
                        """
                    Name: $name
                    Surname: $surname
                    Birth date: $birthDate
                    Gender: $gender
                    Number: $phoneNumber
                    Time created: ${formatLocalDateTime(createdDate)}
                    Time last edit: ${formatLocalDateTime(lastEditeDate)}
                """.trimIndent()
                    )
                }
            }

            is Organization -> {
                val org = contact as Organization
                with(org) {
                    println(
                        """
                        Organization name: $name
                        Address: $address
                        Number: $phoneNumber
                        Time created: $createdDate
                        Time last edit: $lastEditeDate
                    """.trimIndent()
                    )
                }
            }

            else -> {
                throw IllegalStateException("unhandled contact $contact")
            }
        }
        mainPrompt()
    }

    /**
     * Main prompt
     *
     * Wrong answer in test #6
     *
     * This test should contain 3 actions: add, info, exit. You should separate your actions with an empty line.
     *
     * Resolution: add '\n' add start
     *
     */
    private fun mainPrompt() {
        print("\nEnter action (add, remove, edit, count, info, exit): > ")
    }

    private fun listContacts(mainPromptAfter: Boolean = false) {
        if (contacts.isEmpty()) {
            println("No records to list!")
            mainPrompt()
        } else {
            contacts.forEachIndexed { idx, contact ->
                with(contact) {
                    if (contact is Person) {
                        val person = contact as Person
                        println("${idx + 1}. $name ${person.surname}, $phoneNumber")
                    } else if (contact is Organization) {
                        println("${idx + 1}. $name ${contact.address}, $phoneNumber")
                    }
                }
            }
            if (mainPromptAfter) mainPrompt()
        }
    }

    private fun countContacts() {
        println("The Phone Book has ${contacts.size} records.")
        mainPrompt()
    }

    private fun editContact() {
        if (contacts.isEmpty()) {
            println("No records to edit!")
            mainPrompt()
        } else {
            listContacts()
            val choice = promptUser("Select a record: > ").toInt()
            require(choice in 1..contacts.size) { "choice should be 1 to ${contacts.size}, but found '$choice'" }
            val contactToEdit = contacts[choice - 1]
            if (contactToEdit is Person) {
                val fieldToEdit = promptUser("Select a field (name, surname, birth, gender, number): > ")
                when (fieldToEdit) {
                    "name" -> editContactName(contactToEdit)
                    "surname" -> editPersonSurname(contactToEdit)
                    "birth" -> editPersonBirthDate(contactToEdit)
                    "gender" -> editPersonGender(contactToEdit)
                    "number" -> editContactNumber(contactToEdit)
                }
            } else if (contactToEdit is Organization) {
                val fieldToEdit = promptUser("Select a field (address, number): > ")
                when (fieldToEdit) {
                    "address" -> editOrganizationAddress(contactToEdit)
                    "number" -> editContactNumber(contactToEdit)
                }

            }
            println("The record updated!")
            mainPrompt()

        }
    }

    private fun editOrganizationAddress(organization: Organization) {
        val newAddress = promptUser("Enter address: > ")
        organization.address = newAddress
        organization.update()
    }

    private fun editContactName(person: Person) {
        val newName = promptUser("Name:  ")
        person.name = newName
        person.update()
    }

    private fun editPersonSurname(person: Person) {
        val newSurname = promptUser("Surname: ")
        person.surname = newSurname
        person.update()
    }

    private fun editPersonBirthDate(person: Person) {
        val newBirthDate = promptUser("Birth date: ")
        person.birthDate = newBirthDate
        person.update()
    }

    private fun editPersonGender(person: Person) {
        val newGender = promptUser("Gender: ")
        person.gender = newGender
        person.update()

    }

    private fun editContactNumber(contact: Contact) {
        val newPhoneNumber = promptUser("Number: > ")
        contact.phoneNumber = newPhoneNumber
        contact.update()
    }

    private fun removeContact() {
        if (contacts.isEmpty()) {
            println("No records to remove!")
            mainPrompt()
        } else {
            listContactsForInfo()
            val choice = promptUser("Enter index to remove: > ").toInt()
            require(choice in 1..contacts.size) { "choice should be 1 to ${contacts.size}, but found '$choice'" }
            contacts.removeAt(choice - 1)
            println("The record removed!")
            mainPrompt()
        }
    }

    private fun addContact() {
        stage = Stage.ADD_CONTACT
        val contactType = promptUser("Enter the type (person, organization): > ")

        if (contactType == "person") {
            addPerson()
        } else if (contactType == "organization") {
            addOrganization()
        }
        println("The record added.")
        mainPrompt()
    }

    private fun addPerson() {
        val name = promptUser("Enter the name: > ")
        val surname = promptUser("Enter the surname: > ")
        val birthDate = promptUser("Enter the birth date: > ", blanksOkay = true)
        if (!isValidLocalDateText(birthDate)) {
            println("Bad birth date!")
        }
        val gender = promptUser("Enter the gender (M, F): > ", blanksOkay = true)
        if (!isValidGender(gender)) {
            println("Bad gender!")
        }
        val phoneNumber = promptUser("Enter the number: > ")
        contacts.add(Person(name, surname, birthDate, gender, phoneNumber))
    }

    private fun addOrganization() {
        val name = promptUser("Enter the organization name: > ")
        val address = promptUser("Enter the address: > ")
        val phoneNumber = promptUser("Enter the number: > ")
        contacts.add(Organization(name, address, phoneNumber))
    }
}

fun main() {
    val contacts = Contacts()
    contacts.userInput("")
    while (contacts.userInput(readln()));

}