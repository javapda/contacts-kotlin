package com.javapda.contacts

class Contact(var name: String, var surname: String, phoneNumber: String = "[no number]") {
    var phoneNumber = ""
        get() = field
        set(value) {
            if (isValidPhoneNumber(value)) {
                field = value
            } else {
                field = "[no number]"
                println("Wrong number format!")
            }
        }

    init {
        this.phoneNumber = phoneNumber
    }

    fun hasNumber(): Boolean = phoneNumber.trim().isNotBlank()

}