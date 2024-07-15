package com.javapda.contacts

/**
 * Is valid phone number
 * https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
 * Rules:
 *   the very first character may be an optional plus '+' character
 *   parentheses are allowed around group 1 or group 2 only
 *   it is allowed to have no parentheses surrounding any group
 *
 *   group separators: dash '-' or space ' '
 *   minimum of one group - made up of letters and digits
 *   group contents: numbers, uppercase letters, lowercase letters
 *   a group should be at least 2 characters in length, except the first group is allowed to have 1 symbol
 *
 * Example valid phone numbers:
 *  +0 (123) 456-789-ABcd
 *    plus sign +
 *    group 1 is '0'
 *    group 2 is '(123)'
 *    group 3 is '456'
 *    group 4 is '789'
 *    group 5 is 'ABcd'
 *
 *  (123) 234 345-456
 *    no plus sign +
 *    group 1 is '(123)'
 *    group 2 is '234'
 *    group 3 is '345'
 *    group 4 is '456'
 *
 *
 * Example invalid phone numbers:
 *  +0(123)456-789-9999
 *      No separator between 0 and (123), and no separator between (123) and 456
 *
 * @param phoneNumber
 * @return
 */
/**
 * reqVPN
 * valid reqex check
 *
 * @param pattern
 * @param candidate
 */
fun reqVPN(pattern: String, candidate: String) {
    require(pattern.toRegex().matches(candidate)) { "pattern '$pattern', should match '$candidate'" }
}

/**
 * reqIVPN
 * invalid reqex check
 *
 * @param pattern
 * @param candidate
 */
fun reqIVPN(pattern: String, candidate: String) {
    require(!pattern.toRegex().matches(candidate)) { "pattern '$pattern', should match '$candidate'" }
}

fun isValidPhoneNumber(phoneNumber: String): Boolean {
//    return phoneNumber.matches(Regex("^(?:\\+.\\s\\d{2})|(?:\\+\\(\\S{5,7}\\))|^(?:\\d{1,3}\$)|^(?:\\(\\d{1,3}\\)\$)|^(?:\\d{3}.[a-zA-Z]{3})|^(?:\\d{3}.[0-9]{3}.[a-zA-Z]{3})|^(?:\\d{3}.[0-9]{3}.[0-9]{3}\$)|^(?:\\d{3}.\\(\\d{3}\\))\$|^(?:\\d{3}.\\d{2}.[a-z]{2,4}.[0-9]{2})|^(?:\\d{3}.\\(\\d{3}\\).[0-9]{3}\$)|^(?:\\d{3}.\\(\\d{2}\\).\\d{2}.\\d{2})|^(?:\\(\\d{3}\\).\\d{3}\$)|^(?:\\(\\d{3}\\).\\d{3}.\\d{3})"))
//    val regexText="""^[+]?([a-zA-Z0-9\- ]+)*(\([a-zA-Z0-9\- ]+\))([a-zA-Z0-9\- ]+)$"""
    val regexText = """^[+]?([a-zA-Z0-9\- ]+)*(\([a-zA-Z0-9\- ]+\))([a-zA-Z0-9\- ]+)$|^[a-zA-Z0-9\- ]+$"""
    val optionalPlusPrefix = "[+]?"
    val groupSeparator = "[ -]+" // space ' ' or dash '-'
    val groupData = "[a-zA-Z0-9]" // at least one lowercase/uppercase English letter or a numeric digit
    val parenthesizedGroupData="[(]$groupData+[)]"
    require(groupSeparator.toRegex().matches(" "))
    reqVPN(optionalPlusPrefix,"")
    reqVPN(optionalPlusPrefix,"+")
    reqIVPN(optionalPlusPrefix,"X")
    reqVPN(groupSeparator," ")
    reqVPN(groupSeparator,"  ")
    reqVPN(groupSeparator,"-")
    reqVPN(groupSeparator,"- -")
    reqVPN("$groupData+","aZz")
    reqIVPN("$groupData+","a@Zz")
//    val singleGroup = "${optionalPlusPrefix}(${groupData}+)|${optionalPlusPrefix}([(]${groupData}+[)])"
    val singleGroup = "${optionalPlusPrefix}(${groupData}+)|${optionalPlusPrefix}(${parenthesizedGroupData}+)"
    reqVPN(singleGroup,"0")
    reqVPN(singleGroup,"123")
    reqVPN(singleGroup,"(0)")
    reqVPN(singleGroup,"+0")
    reqVPN(singleGroup,"+(0)")
    reqVPN(singleGroup,"+01234567890Abcd")
    reqVPN(singleGroup,"+(01234567890Abcd)")
    reqIVPN(singleGroup,"+01234567890Abcd OTHER")
    reqIVPN(singleGroup,"+01234567890Abcd-OTHER")
    reqIVPN(singleGroup,"+01234567890Abcd- -OTHER")
    reqIVPN(singleGroup,"+(01234567890Abcd) OTHER")
    reqIVPN(singleGroup,"+(01234567890Abcd)-OTHER")
    reqIVPN(singleGroup,"+(01234567890Abcd)OTHER")
    val twoGroupsNoParens = "${optionalPlusPrefix}(${groupData}+)${groupSeparator}+(${groupData}{2,})"
    reqVPN(twoGroupsNoParens,"+01234567890Abcd OTHER")
    reqVPN(twoGroupsNoParens,"+C OTHER")
    reqIVPN(twoGroupsNoParens,"+01234567890Abcd 1")
    reqIVPN(twoGroupsNoParens,"+0 1")
    reqVPN(twoGroupsNoParens,"+01234567890Abcd-OTHER")
    reqVPN(twoGroupsNoParens,"+01234567890Abcd- OTHER")
    reqVPN(twoGroupsNoParens,"+01234567890Abcd- - OTHER")
    val twoGroupsParensGroup1 = "${optionalPlusPrefix}(${parenthesizedGroupData}+)${groupSeparator}+(${groupData}+)"
    reqVPN(twoGroupsParensGroup1,"+(01234567890Abcd) OTHER")
    reqVPN(twoGroupsParensGroup1,"+(01234567890Abcd)-OTHER")
    reqIVPN(twoGroupsParensGroup1,"+01234567890Abcd (OTHER)")
    val twoGroupsParensGroup2 = "${optionalPlusPrefix}(${groupData}+)${groupSeparator}+(${parenthesizedGroupData}+)"
    reqVPN(twoGroupsParensGroup2,"+01234567890Abcd (OTHER)")
    reqVPN(twoGroupsParensGroup2,"+0 (OTHER)")
    val twoGroups = "$twoGroupsNoParens|$twoGroupsParensGroup1|$twoGroupsParensGroup2"
    reqVPN(twoGroups,"+0 (OTHER)")
    reqVPN(twoGroups,"+01234567890Abcd (OTHER)")
    reqVPN(twoGroups,"+(01234567890Abcd) OTHER")
    reqVPN(twoGroups,"+01234567890Abcd OTHER")
    reqVPN(twoGroups,"01234567890Abcd OTHER")
    val beyondTwoGroupWithSeparator = "(${singleGroup}|${twoGroups})(${groupSeparator}+(${groupData}{2,}))*"
    reqVPN(beyondTwoGroupWithSeparator,"123")
    reqVPN(beyondTwoGroupWithSeparator,"01234567890Abcd OTHER")
    reqVPN(beyondTwoGroupWithSeparator,"01234567890Abcd (OTHER)")
    reqIVPN(beyondTwoGroupWithSeparator,"01234567890Abcd (OTHER) 2 34")
    return phoneNumber.matches(Regex(beyondTwoGroupWithSeparator))
}

fun isInvaliPhoneNumber(phoneNumber: String): Boolean = !isValidPhoneNumber(phoneNumber)
