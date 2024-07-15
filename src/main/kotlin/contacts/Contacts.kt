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

class Contacts {
    enum class Stage {
        MAIN_MENU, ADD_CONTACT, EDIT_CONTACT, REMOVE_CONTACT
    }

    private val contacts = mutableListOf<Contact>()
    private var stage = Stage.MAIN_MENU
    private fun promptUser(prompt: String): String {
        print(prompt)
        var line: String = ""
        while (line == "") {
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
            "list" -> listContacts()
            else -> mainPrompt()
        }
        return true
    }

    private fun mainPrompt() {
        print("Enter action (add, remove, edit, count, list, exit): > ")
    }

    private fun listContacts() {
        if (contacts.isEmpty()) {
            println("No records to list!")
            mainPrompt()
        } else {
            contacts.forEachIndexed { idx, contact ->
                with(contact) {
                    println("${idx + 1}. $name $surname, $phoneNumber")
                }
            }
            mainPrompt()
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
            val fieldToEdit = promptUser("Select a field (name, surname, number): > ")
            when (fieldToEdit) {
                "name" -> editContactName(contactToEdit)
                "surname" -> editContactSurname(contactToEdit)
                "number" -> editContactNumber(contactToEdit)
            }
            println("The record updated!")
            mainPrompt()

        }
    }

    private fun editContactName(contact: Contact) {
        val newName = promptUser("Enter name: > ")
        contact.name = newName
    }

    private fun editContactSurname(contact: Contact) {
        val newSurname = promptUser("Enter surname: > ")
        contact.surname = newSurname
    }

    private fun editContactNumber(contact: Contact) {
        val newPhoneNumber = promptUser("Enter number: > ")
        contact.phoneNumber = newPhoneNumber
    }

    private fun removeContact() {
        if (contacts.isEmpty()) {
            println("No records to remove!")
            mainPrompt()
        } else {
            listContacts()
            val choice = promptUser("Select a record: > ").toInt()
            require(choice in 1..contacts.size) { "choice should be 1 to ${contacts.size}, but found '$choice'" }
            contacts.removeAt(choice - 1)
            println("The record removed!")
            mainPrompt()
        }
    }

    private fun addContact() {
        stage = Stage.ADD_CONTACT
        val name = promptUser("Enter the name: > ")
        val surname = promptUser("Enter the surname: > ")
        val phoneNumber = promptUser("Enter the number: > ")
        contacts.add(Contact(name, surname, phoneNumber))
        println("The record added.")
        mainPrompt()
    }
}

fun main() {
    val contacts = Contacts()
    contacts.userInput("")
    while (contacts.userInput(readln()));


}