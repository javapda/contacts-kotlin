package com.javapda.contacts

import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

/**
 * Contacts
 *
 * HINT Stage 2/4:
 *
 * This stage is not about the regexp. I mean, it's not the most essential part of the task...
 *
 * @constructor Create empty Contacts
 */

class Contacts(preload: Boolean = false, val saveFile: String? = null) {
    enum class Stage {
        MAIN_MENU, ADD_CONTACT, EDIT_CONTACT, REMOVE_CONTACT, SEARCH, RECORD, LIST
    }

    private var searchResults: MutableList<Contact> = mutableListOf()
    private var selectedContact: Contact? = null
    private val contacts = mutableListOf<Contact>()
    private var stage = Stage.MAIN_MENU

    init {
        if (saveFileEnabled()) {
            //saveContactsToFile()
            readFromSaveFile()
        }
        if (preload) {
            contacts.addAll(
                listOf<Contact>(
                    Person("John", "Smith", "", "", "(212) 234-4321"),
                    Organization("Apple, Inc.", "1 Apple Park Way", "(408) 996-1010"),
                    Person("Ben", "Franklin", "1706-01-17", "M", "(212) 998-2651"),
                    Organization("Tesla", "123 Main St.", "(212) 998-2651"),
                    Organization("Central Bank", "25354 Jed Way.", "(212) 998-2651"),
                    Person("Alice", "Wonderland", "", "F", "+123123 (123) 12-23-34-45"),
                    Organization("Centurion Adams", "5 E. Broadway", "(793) 234-1523"),
                    Organization("Decent Pizza Shop", "987 Mozza Blvd", "(887) 843-8534"),
                    Organization("Car Shop", "Wall St. 3", "+0 (123) 456-789-9999"),
                )
            )
            if (saveFileEnabled()) {
                writeToSaveFile()
            }
        }
    }

    private fun writeToSaveFile() {
        if (saveFile != null) {
            val pathToJsonFile = Path(saveFile)
            pathToJsonFile.writeText(ContactListJsonGenerator().toJson(contacts))
            readFromSaveFile()
        }
    }

    private fun readFromSaveFile() {
        if (saveFile != null) {
            val pathToJsonFile = Path(saveFile)
            if (pathToJsonFile.exists()) {
                contacts.clear()
                contacts.addAll(ContactListJsonGenerator().fromJson(pathToJsonFile.readText()))
            }

        } else {
            throw IllegalArgumentException("saveFile is NULL")
        }
    }

    fun saveFileEnabled() = saveFile != null
    private fun saveContactsToFile() {
        if (saveFileEnabled()) {

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

    private fun searchInput(userInput: String) {
        if (isNumeric(userInput)) {
            val contactIndex = userInput.toInt()
            if (contactIndex in 1..searchResults.size) {
                selectedContact = searchResults[contactIndex - 1]
                recordSelectedContact()
            } else {
                println("BAD CHOICE NUMBER : $contactIndex is not in the range of 1 to ${contacts.lastIndex + 1}")
                listContacts()
            }
            return
        }
        when (userInput) {

            "again" -> searchContacts()
            "back" -> {
                stage = Stage.MAIN_MENU
                mainPrompt()
            }

            else -> throw IllegalStateException("searchInput, unsupported userInput='$userInput', stage='$stage'")
        }
    }

    fun userInput(userInput: String): Boolean {
        if (userInput == "" && stage == Stage.MAIN_MENU) {
            mainPrompt()
            return true
        } else if (userInput == "exit") {
            return false
        }
        fun menuInput(userInput: String) {
            when (userInput) {
                "add" -> addContact()
                "list" -> listContacts()
                "search" -> searchContacts()
                "count" -> countContacts()
                else -> throw IllegalStateException("menuInput - no support for stage=$stage, with input '$userInput'")
            }
        }
        when (stage) {
            Stage.MAIN_MENU -> menuInput(userInput)
            Stage.LIST -> listInput(userInput)
            Stage.RECORD -> recordInput(userInput)
            Stage.SEARCH -> searchInput(userInput)
            else -> throw IllegalStateException("No support for stage=$stage, with input '$userInput'")
        }

        return true
    }

    private fun recordInput(userInput: String) {
        when (userInput) {
            "edit" -> editSelectedContact()
            "delete" -> removeContact(selectedContact)
            "menu" -> {
                stage = Stage.MAIN_MENU
                mainPrompt()
            }

            else -> throw IllegalStateException("recordInput, userInput=$userInput, stage=$stage")
        }

    }

    private fun isNumeric(toCheck: String): Boolean {
        return toCheck.toIntOrNull() != null
    }

    private fun listInput(userInput: String) {
        if (userInput == "back") {
            stage = Stage.MAIN_MENU
            mainPrompt()
            return
        }
        if (isNumeric(userInput)) {
            val contactIndex = userInput.toInt()
            if (contactIndex in 1..contacts.size) {
                selectedContact = contacts[contactIndex - 1]
                recordSelectedContact()
            } else {
                println("BAD CHOICE NUMBER : $contactIndex is not in the range of 1 to ${contacts.lastIndex + 1}")
                listContacts()
            }
        }
    }

    private fun recordSelectedContact() {
        if (selectedContact == null)
            throw IllegalStateException("recordSelectedContact, no selected contact, stage=$stage")
        stage = Stage.RECORD
        presentContact(selectedContact!!)
        print("[record] Enter action (edit, delete, menu): > ")
    }

    private fun presentContact(contact: Contact) {
        if (contact is Person) {
            with(contact) {
                println(
                    """
                Name: $name
                Surname: $surname
                Birth date: $birthDate
                Gender: $gender
                Number: $phoneNumber
                Time created: $createdDate
                Time last edit: $lastEditedDate
            """.trimIndent()
                )
            }
        } else if (contact is Organization) {
            with(contact) {
                println(
                    """
                    Organization name: $name
                    Address: $address
                    Number: $phoneNumber
                    Time created: $createdDate
                    Time last edit: $lastEditedDate
                """.trimIndent()
                )
            }
        }

    }

    private fun searchContacts() {
        stage = Stage.SEARCH
        val searchQuery = promptUser("Enter search query: > ")
        searchResults.clear()
        searchResults.addAll(contacts.filter { contact ->
            contact.searchableText().contains(searchQuery, ignoreCase = true)
        })
        println("Found ${searchResults!!.size} results${if (searchResults!!.isNotEmpty()) ":" else "."}")
        presentContacts(searchResults)
        print("[search] Enter action (${if (searchResults.isNotEmpty()) "[number], " else ""}back, again): > ")
    }

    private fun listContactsForInfo() {
        presentContacts()
    }

    private fun presentContacts(contacts: List<Contact> = this.contacts) {
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
                    Time last edit: ${formatLocalDateTime(lastEditedDate)}
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
                        Time last edit: $lastEditedDate
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
        stage = Stage.MAIN_MENU
        print("\n[menu] Enter action (add, list, search, count, exit): > ")
//        print("\n[menu] Enter action (add, remove, edit, count, info, exit): > ")
    }

    private fun listContacts(mainPromptAfter: Boolean = false) {
        if (contacts.isEmpty()) {
            println("No records to list!")
            mainPrompt()
        } else {
            stage = Stage.LIST
            contacts.forEachIndexed { idx, contact ->
                with(contact) {
//                    println("${idx + 1}. $name")
                    if (contact is Person) {
                        val person = contact as Person
                        println("${idx + 1}. $name ${person.surname}") //, $phoneNumber")
                    } else if (contact is Organization) {
                        println("${idx + 1}. $name") // ${contact.address}, $phoneNumber")
                    }
                }
            }
            print("[list] Enter action ([number], back): > ")
        }
    }

    private fun countContacts() {
        println("The Phone Book has ${contacts.size} records.")
        mainPrompt()
    }

    private fun editSelectedContact() {
        stage = Stage.EDIT_CONTACT
        if (selectedContact == null) {
            throw IllegalStateException("no selected contact, stage=$stage")
        }
        if (contacts.isEmpty()) {
            println("No records to edit!")
            mainPrompt()
        } else {
            val contactToEdit = selectedContact
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
                val fieldToEdit = promptUser("Select a field (name, address, number): > ")
                when (fieldToEdit) {
                    "name" -> editContactName(contactToEdit)
                    "address" -> editOrganizationAddress(contactToEdit)
                    "number" -> editContactNumber(contactToEdit)
                }
            }
            println("Saved")
            presentContact(selectedContact!!)
            recordSelectedContact()

            //println("The record updated!")

//            stage = Stage.MAIN_MENU
//            mainPrompt()

        }
    }


    private fun editOrganizationAddress(organization: Organization) {
        val newAddress = promptUser("Enter address: > ")
        organization.address = newAddress
        organization.update()
        writeToSaveFile()
    }

    private fun editContactName(contact: Contact) {
        val newName = promptUser("Name:  ")
        contact.name = newName
        contact.update()
        writeToSaveFile()
    }

    private fun editPersonSurname(person: Person) {
        val newSurname = promptUser("Surname: ")
        person.surname = newSurname
        person.update()
        writeToSaveFile()
    }

    private fun editPersonBirthDate(person: Person) {
        val newBirthDate = promptUser("Birth date: ")
        person.birthDate = newBirthDate
        person.update()
        writeToSaveFile()
    }

    private fun editPersonGender(person: Person) {
        val newGender = promptUser("Gender: ")
        person.gender = newGender
        person.update()
        writeToSaveFile()

    }

    private fun editContactNumber(contact: Contact) {
        val newPhoneNumber = promptUser("Number: > ")
        contact.phoneNumber = newPhoneNumber
        contact.update()
        writeToSaveFile()
    }

    private fun removeContact(contact: Contact? = null) {
        if (contacts.isEmpty()) {
            println("No records to remove!")
            mainPrompt()
        } else if (contact != null) {
            contacts.remove(contact)
            writeToSaveFile()
            stage = Stage.MAIN_MENU
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
        writeToSaveFile()
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

fun saveFileFromArgs(args: Array<String>): String? =
    if (args.isNotEmpty() && args.size == 2) {
        println("${args[0]} ${args[1]}")
        if (args[0] == "open") args[1] else null
    } else {
        null
    }


fun main(args: Array<String>) {

    val contacts =
        Contacts(saveFile = saveFileFromArgs(if (args.isNotEmpty()) args else arrayOf("open", "phonebook.db")))
    contacts.userInput("")
    while (contacts.userInput(readln()));

}