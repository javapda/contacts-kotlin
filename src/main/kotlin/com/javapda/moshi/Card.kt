package com.javapda.moshi

import com.javapda.contacts.LocalDateTimeAdapter
import com.javapda.contacts.Organization
import com.javapda.contacts.Person
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Suit
 * https://github.com/square/moshi/blob/master/examples/src/main/java/com/squareup/moshi/recipes/models/Suit.java
 * https://kotlinlang.org/docs/enum-classes.html
 * @constructor Create empty Suit
 */
enum class Suit {
    CLUBS, DIAMONDS, HEARTS, SPADES;

    override fun toString(): String {
        return name.substring(0, 1)
    }
}

/**
 * Card
 * https://github.com/square/moshi/blob/master/examples/src/main/java/com/squareup/moshi/recipes/models/Card.java
 *
 * @property rank
 * @property suit
 * @constructor Create empty Card
 */
data class Card(val rank: Char = '8', val suit: Suit = Suit.SPADES) {
    override fun toString(): String {
        return "%s%s".format(rank, suit)
    }
}

class BlackjackHand(hiddenCard: Card? = null, visibleCards: List<Card>? = null) {
    @Json(name = "hc")
    var hidden_card: Card? = hiddenCard;

    @Json(name = "vc")
    var visible_cards: List<Card>? = visibleCards

    override fun toString(): String {
        return "hidden=$hidden_card,visible=$visible_cards"
    }

}

class Player(val username: String, @Json(name = "lucky number") val luckyNumber: Int) {
    override fun toString(): String {
        return "$username gets lucky with $luckyNumber"
    }
}

class Tournament(val name: String, val location: String, val start: Date) {
    override fun toString(): String {
        return "$name at $location on $start"
    }
}

data class Monkey(val name: String, val age: Int = 16)


fun main() {
    doOrganization()
    "--".repeat(34).let(::println)
    doPersonList()
    "--".repeat(34).let(::println)
    doPerson()
    "--".repeat(34).let(::println)
    doBlackjackHandList()
    "--".repeat(34).let(::println)
//    doBlackjackHand()
//    "--".repeat(34).let(::println)
//    doMoshiCard()
//    "--".repeat(34).let(::println)
//    doMoshiCardList()
//    "--".repeat(34).let(::println)
//    doMoshiMonkey()
//    "--".repeat(34).let(::println)
//    doMoshiMonkeyList()
//    "--".repeat(34).let(::println)
//    doMoshiBlackjackHand()
    doLocalDateTime()
}

fun doOrganization() {
    val type = Types.newParameterizedType(Organization::class.java, Date::class.java, LocalDateTime::class.java)
    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).add(LocalDateTimeAdapter()).build()
    val organizationAdapter = moshi.adapter<Organization?>(type)
    val organization = organizationAdapter.fromJson(
        """
            {
            "name" : "Apple, Inc.",
            "address" : "1 Apple Parkway, Cupertino, CA 95014",
            "phone_number" : "(212) 555-1212",
            "created_date" : "2024-05-21T11:34:23.4086892", 
            "last_edited_date" : "2024-05-25T15:12:33"
            }
    """.trimIndent()
    )
    println(organization)
    println(organizationAdapter.toJson(organization))
}

fun doLocalDateTime() {
    val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    println(formatter.format(LocalDateTime.now()))
}

fun doPersonList() {
    val type =
        Types.newParameterizedType(List::class.java, Person::class.java, Date::class.java, LocalDateTime::class.java)
    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).add(LocalDateTimeAdapter()).build()
    val personListAdapter = moshi.adapter<List<Person?>>(type)
    val personList = personListAdapter.fromJson(
        """
          [
            {
            "name" : "Wilma",
            "surname" : "Flintstone",
            "dob" : "0031-04-22",
            "gender" : "F",
            "phone_number" : "+0 (599) 653-1251",
            "created_date" : "2024-07-16T11:50", 
            "last_edited_date" : "2024-07-16T11:53"
            },
            {
            "name" : "Hector",
            "surname" : "Smith",
            "dob" : "2001-12-03",
            "gender" : "M",
            "phone_number" : "(212) 555-1212",
            "created_date" : "2024-07-16T11:34:23.4086892", 
            "last_edited_date" : "2024-07-16T11:47:33.3257953"
            }
          ]
    """.trimIndent()
    )
    println(personList)
    println(personListAdapter.toJson(personList))
}

fun doPerson() {
    val type = Types.newParameterizedType(Person::class.java, Date::class.java, LocalDateTime::class.java)
    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).add(LocalDateTimeAdapter()).build()
    val personAdapter = moshi.adapter<Person?>(type)
    val person = personAdapter.fromJson(
        """
            {
            "name" : "Hector",
            "surname" : "Smith",
            "dob" : "2001-12-03",
            "gender" : "M",
            "phone_number" : "(212) 555-1212",
            "created_date" : "2024-07-16T11:34:23.4086892", 
            "last_edited_date" : "2024-07-16T11:47:33.3257953"
            }
    """.trimIndent()
    )
    println(person)
    println(personAdapter.toJson(person))
}

fun doBlackjackHandList() {
    val type = Types.newParameterizedType(List::class.java, BlackjackHand::class.java, Card::class.java)
    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val blackjackHandListAdapter = moshi.adapter<List<BlackjackHand?>>(type)
    val blackjackList = blackjackHandListAdapter.fromJson(
        """
            [
            {"hc":{"rank":"8", "suit":"SPADES"}, "vc": [{"rank":"8", "suit":"SPADES"},{"rank":"J", "suit":"HEARTS"}]}
             ]
    """.trimIndent()
    )
    println(blackjackList)
    println(blackjackHandListAdapter.toJson(blackjackList))

}

fun doBlackjackHand() {
    val type = Types.newParameterizedType(BlackjackHand::class.java, Card::class.java)
    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val blackjackHandListAdapter = moshi.adapter<BlackjackHand?>(type)
    val blackjack = blackjackHandListAdapter.fromJson(
        """
            {"hc":{"rank":"8", "suit":"SPADES"}, "vc": [{"rank":"8", "suit":"SPADES"},{"rank":"J", "suit":"HEARTS"}]} 
    """.trimIndent()
    )
    println(blackjack)
    println(blackjackHandListAdapter.toJson(blackjack))

}

fun doMoshiCardList() {
    val type = Types.newParameterizedType(List::class.java, Card::class.java)
    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val cardListAdapter = moshi.adapter<List<Card?>>(type)
    val cardList = cardListAdapter.fromJson(
        """
        [
            {"rank":"A", "suit": "CLUBS"}, 
            {"rank":"K", "suit": "DIAMONDS"} 
        ]
    """.trimIndent()
    )
    println(cardList)
    println(cardListAdapter.toJson(cardList))

}

fun doMoshiCard() {
//    val type = Types.newParameterizedType(Card::class.java)
    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val cardAdapter = moshi.adapter<Card?>(Card::class.java)
    val card = cardAdapter.fromJson(
        """
        {
        "rank":"3",
        "suit":"HEARTS"
        }
    """.trimIndent()
    )
    println(card)
    println(cardAdapter.toJson(card))
}

fun doMoshiMonkeyList() {
    val type = Types.newParameterizedType(List::class.java, Monkey::class.java)
    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val monkeyListAdapter = moshi.adapter<List<Monkey?>>(type)
    val monkeyList = monkeyListAdapter.fromJson(
        """
        [{"name":"n1", "age": 3}, {"name":"John", "age":25}]
    """.trimIndent()
    )
    println(monkeyList)


}

fun doMoshiMonkey() {
    val monkeyJson = """
        {
          "name": "Caesar",
          "age": 43
        }
    """.trimIndent()
    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//    val valjsonAdapter: JsonAdapter<BlackjackHand> = moshi.adapter(BlackjackHand::class.java)
    val monkeyAdapter = moshi.adapter(Monkey::class.java)!!
    val monkey = monkeyAdapter.fromJson(monkeyJson)
    println(monkey)
    println(monkeyAdapter.toJson(monkey))
    val jsonAdapter = moshi.adapter(Monkey::class.java)!!

    val blackjackHand = jsonAdapter.fromJson(monkeyJson)
    println(blackjackHand)
}

