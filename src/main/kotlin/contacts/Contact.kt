package com.javapda.contacts

import java.time.LocalDateTime

abstract class Contact(
    var name: String,
    phoneNumber: String = "[no number]",
    val createdDate: LocalDateTime = LocalDateTime.now(),
    var lastEditeDate: LocalDateTime = createdDate
) {
    fun update() {
        lastEditeDate = LocalDateTime.now()
    }
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

class Person(
    name: String,
    var surname: String,
    birthDate: String = "[no data]",
    gender: String = "[no data]",
    phoneNumber: String = "[no data]"
) : Contact(name, phoneNumber) {
    var birthDate: String = ""
        get() = field
        set(value) {
            if (isValidLocalDateText(value)) {
                field = value
            } else {
                field = "[no data]"
//                println("Wrong number format!")
            }
        }
    var gender: String = ""
        get() = field
        set(value) {
            if (isValidGender(value)) {
                field = value
            } else {
                field = "[no data]"
//                println("Wrong gender format!")
            }
        }

    init {
        this.birthDate = birthDate
        this.gender = gender
    }
}


class Organization(name: String, var address: String, phoneNumber: String = "[no number]") : Contact(name, phoneNumber)