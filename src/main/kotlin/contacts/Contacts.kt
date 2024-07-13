package com.javapda.contacts

/**
 * Contacts
 *
 * @constructor Create empty Contacts
 */
class Contacts {
    fun promptUser(prompt: String): String {
        println(prompt)
        var line: String = ""
        while (line == "") {
            print("> ")
            line = readln()
        }
        return line
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
}

fun main() {
    Contacts().stage1of4()
}