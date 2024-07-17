package com.javapda.contacts

import com.squareup.moshi.Json
import java.time.LocalDateTime

enum class ContactType {
    PERSON,
    ORGANIZATION
}

sealed abstract class Contact(
    @Json(name = "contact_type") contactType: ContactType,
    @Json(name = "name")
    var name: String,
    phoneNumber: String = "[no number]"
) {
    @Json(name = "created_date")
    var createdDate: LocalDateTime

    @Json(name = "last_edited_date")
    var lastEditedDate: LocalDateTime
    fun update() {
        lastEditedDate = LocalDateTime.now()
    }

    @Json(name = "phone_number")
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
        this.createdDate = LocalDateTime.now()
        this.lastEditedDate = this.createdDate

    }

    fun hasNumber(): Boolean = phoneNumber.trim().isNotBlank()
    abstract fun searchableText(): String
}

class Person(
    name: String,
    @Json(name = "surname")
    var surname: String,
    birthDate: String = "[no data]",
    gender: String = "[no data]",
    phoneNumber: String = "[no data]",
    personJsonGenerator: JsonGenerator<Person> = PersonJsonGenerator()
) : Contact(ContactType.PERSON, name, phoneNumber), JsonGenerator<Person> by personJsonGenerator {
    @Json(name = "dob")
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


    @Json(name = "gender")
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

    override fun searchableText(): String {
        return "$name $surname"
    }

}

class Organization(
    name: String,
    @Json(name = "address") var address: String,
    phoneNumber: String = "[no number]",
    organizationJsonGenerator: JsonGenerator<Organization> = OrganizationJsonGenerator()
) :
    Contact(ContactType.ORGANIZATION, name, phoneNumber), JsonGenerator<Organization> by organizationJsonGenerator {
    override fun searchableText(): String {
        return name // $address"
    }
}