# [contacts-kotlin](https://github.com/javapda/contacts-kotlin)
* based on the Hyperskill [Contacts (Kotlin)](https://hyperskill.org/projects/261/) project
* project [contacts-kotlin](https://github.com/javapda/contacts-kotlin) on github
* [discussion stage 4/4](https://hyperskill.org/projects/261/stages/1324/implement#comment)

## build and run
```
.\gradlew.bat clean build 
java -jar .\build\libs\contacts-kotlin-0.0.1-SNAPSHOT-all.jar
# - or - 
#  java -cp  .\build\libs\coffee-machine-kotlin-0.0.1-SNAPSHOT-all.jar  com.javapda.contacts.ContactsKt
```


# resources
* [regex tester](https://www.freeformatter.com/regex-tester.html)

## Hyperskill
* [my profile](https://hyperskill.org/profile/615178637)
* [Troubleshooting: no tests have run](https://plugins.jetbrains.com/plugin/10081-jetbrains-academy/docs/troubleshooting-guide.html#no_tests_have_run)

## Solve in IDE?
* When you go to enter your solution in [Stage 4/4](https://hyperskill.org/projects/261/stages/1324/implement) 
you discover you must use the Hyperskill IDE integration. There is a button labeled "Solve in IDE" and
the "Code Editor" tab is disabled with tooltip text of _**"You can solve it only in IDE, no web-version available here"**_.
* Once the "Solve in IDE" button is clicked there is some interaction with IntelliJ IDEA
and two green labeled areas:
  1. IDE responding
  2. the JetBrains Academy plugin responding
* Issue?
```courseignore
FEEDBACK:
Failed to launch checking. <a href="reload_gradle">Reload Gradle project</a>. For more information, see <a href="https://plugins.jetbrains.com/plugin/10081-jetbrains-academy/docs/troubleshooting-guide.html#no_tests_have_run">the Troubleshooting guide</a>
```
### plugin path issue?
* when attempting to run a simple main() in the IDE connected to Hyperskill, the errors below came up. The
fix was to go into Project Settings (ctrl-alt-shift-S) and go to the Project tab. There the setting of the SDK was
incorrect - was pointing to a WSL version of the JDK. Once I changed it to "17 java version 17.0.9" things began to work.
```courseignore
Kotlin: Plugin classpath entry points to a non-existent location: /home/jkroub/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-scripting-jvm/1.8.20/51c8efbe177ebcaa89c82d01663c60060a120dd2/kotlin-scripting-jvm-1.8.20.jar

Kotlin: Plugin classpath entry points to a non-existent location: /home/jkroub/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-scripting-common/1.8.20/f19996e3a40658541fe2108c483fd3301c4a3416/kotlin-scripting-common-1.8.20.jar

Kotlin: Plugin classpath entry points to a non-existent location: /home/jkroub/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib/1.8.20/e72fc5e03ec6c064c678a6bd0d955c88d55b0c4a/kotlin-stdlib-1.8.20.jar

Kotlin: Plugin classpath entry points to a non-existent location: /home/jkroub/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-common/1.8.20/5eddaaf234c8c49d03eebeb6a14feb7f90faca71/kotlin-stdlib-common-1.8.20.jar

Kotlin: Plugin classpath entry points to a non-existent location: /home/jkroub/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-script-runtime/1.8.20/c850771e723701f9d63dbcf641429c0f29290074/kotlin-script-runtime-1.8.20.jar
```

## Stage 4 Dependencies
### build.gradle.kts (kotlin)
```courseignore
plugins {
    kotlin("jvm") version "1.9.23"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("com.google.devtools.ksp").version("1.6.10-1.0.4")

}
dependencies {
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
    implementation("com.squareup.moshi:moshi-adapters:1.15.1")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.1")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.3")
}

```

### build.gradle (groovy) : Hyperskill Solve in IDE
```courseignore
    dependencies {
        implementation "com.squareup.moshi:moshi-kotlin:1.15.1"
        implementation "com.squareup.moshi:moshi-adapters:1.15.1"
        testImplementation 'com.github.hyperskill:hs-test:release-SNAPSHOT'
        testImplementation "org.junit.jupiter:junit-jupiter:5.9.3"
        testImplementation "org.junit.jupiter:junit-jupiter-params:5.9.3"
    }

```

## Stage 4/4 : Searching
### Description
Our temporary solution in the previous stage was bad because of every time you want to interact with concrete classes you must check the Boolean property then apply different code according to the concrete class. So far so good, you implement this behavior every time you need to. However, in a larger application, there can be 100 places or more where you must do this. And at the end of the day, a new feature request might come in: implement a third type of records, something that represents an automated system with robots serving the calls. You would be very annoyed that you were forced to find all the places where you interact with concrete classes.

The solution to this problem is a **polymorphism**.

Basically, you need to implement the functionality in one place inside a concrete class. All of the derived classes should implement this method, and in the base class, there should be an abstract method. In the code, you actually call this abstract method and get different code executions in the derived classes.

To solve your problem, you should create several methods:

1. A method that returns all of the possible properties this class is able to change.
2. A method that takes a string that represents a property that the class is able to change and its new value.
3. A method that takes a string representation of the property and returns the value of this property.
After that, you don't need any Boolean workarounds and type casts; the code will be nice and clean.

Also, in this stage, you should implement saving to a file and loading from a file. You can save the Contacts using serialization. You should specify a file you are working with by a command-line argument. This would automatically save the Contacts on the hard drive after each action that modifies data. If you don't specify an argument, then you should create a new Contacts and keep it in memory. If you specify a file that doesn't exist, you should create an empty Contacts and save all changes to the newly created file.

Also, in this stage, you should implement search functionality. For this, you can append all of the values from all of the properties and check if this string contains a search request. It should support regular expressions, too! And, of course, it should be case insensitive.

Use an empty line to separate different actions, like in the example.

### My Notes
* so we will need to save content to file and then re-load it. How about using [moshi-kotlin](https://github.com/square/moshi/)?
* moshi : [examples - recipes](https://github.com/square/moshi/tree/master/examples/src/main/java/com/squareup/moshi/recipes)
* [moshi javadoc](https://square.github.io/moshi/1.x/moshi/)
#### Moshi Todo's
* DONE: add dependency to build.gradle.kts [moshi and kotlin-reflect]
* TODO: add simple save and reload
* TODO: figure out how to save and reload a list of Contact objects
```courseignore
dependencies {
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
}
```

### Example
Below is an example of how your output might look. The symbol > represents the user input.
```
open phonebook.db

[menu] Enter action (add, list, search, count, exit): > count
The Phone Book has 6 records.

[menu] Enter action (add, list, search, count, exit): > search
Enter search query: > cent
Found 3 results:
1. Central Bank
2. Centurion Adams
3. Decent Pizza Shop

[search] Enter action ([number], back, again): > again
Enter search query: > shop
Found 2 results:
1. Decent Pizza Shop
2. Car shop

[search] Enter action ([number], back, again): > 2
Organization name: Car shop
Address: Wall St. 3
Number: +0 (123) 456-789-9999
Time created: 2018-01-01T00:03
Time last edit: 2018-04-29T11:34

[record] Enter action (edit, delete, menu): > edit
Select a field (name, address, number): > name
Enter name: > New Car Shop
Saved
Organization name: New Car Shop
Address: Wall St. 3
Number: +0 (123) 456-789-9999
Time created: 2018-01-01T00:03
Time last edit: 2018-11-20T11:04

[record] Enter action (edit, delete, menu): > menu

[menu] Enter action (add, list, search, count, exit): > search
Enter search query: > new
Found 1 result:
1. New Car Shop

[search] Enter action ([number], back, again): > back

[menu] Enter action (add, list, search, count, exit): > list
1. New Car Shop
2. Decent Pizza Shop
3. Central Bank
4. Centurion Adams
5. John Smith
6. Alice Wonderlanded

[list] Enter action ([number], back): > 6
Name: Alice
Surname: Wonderlanded
Birth date: [no data]
Gender: F
Number: +123123 (123) 12-23-34-45
Time created: 2018-03-12T11:21
Time last edit: 2018-03-12T11:21

[record] Enter action (edit, delete, menu): > edit
Select a field (name, surname, birth, gender, number): > number
Enter number: > +23 (321) 12-12 12 12
Saved
Name: Alice
Surname: Wonderlanded
Birth date: [no data]
Gender: F
Number: +23 (321) 12-12 12 12
Time created: 2018-03-12T11:21
Time last edit: 2018-11-20T11:07

[record] Enter action (edit, delete, menu): > menu

[menu] Enter action (add, list, search, count, exit): > exit
```

## Stage 3/4 : [Upgrade your contacts](https://hyperskill.org/projects/261/stages/1323/implement)
### Description
In this stage, you will write a program that can store not only information about people but also organizations.

In the real app, you can also store phone numbers of different companies, like your favorite pizza shop or your bank. These organizations don't have a name or a surname; they have an organization name and an address.

Additionally, a person can have a birth date and gender, but a company can't have that.

Let's use **inheritance** to solve this issue. There should be a base class containing information relevant to both an organization and a person, like a phone number. And there should be two classes that inherit this base class: a class that represents an organization and a class that represents a person.

The list of records should contain both people and organizations. You can accomplish that if the list with records contains elements of the base class, not the specific classes.

There is one problem with that: how can you edit the properties that correspond to specific classes, like the address of an organization? One of the solutions is to create a final Boolean property isPerson in the base class. After that, when editing the record, first check this property, then cast to the corresponding class and then edit the property. This is a bad, workaround solution, but we will write a more general solution in the next stage.

Also, in this stage, you can improve the base class so that it has more than one property. You should implement properties that store the date of this record creation and the date of the last edit.

Use an empty line to separate different actions, like in the example.
### Example
Below is an example of how your output might look. The symbol > represents the user input.
```
Enter action (add, remove, edit, count, info, exit): > add
Enter the type (person, organization): > person
Enter the name: > John
Enter the surname: > Smith
Enter the birth date: >
Bad birth date!
Enter the gender (M, F): >
Bad gender!
Enter the number: > +0 (123) 456-789-ABcd
The record added.

Enter action (add, remove, edit, count, info, exit): > add
Enter the type (person, organization): > organization
Enter the organization name: > Pizza Shop
Enter the address: > Wall St. 1
Enter the number: > +0 (123) 456-789-9999
The record added.

Enter action (add, remove, edit, count, info, exit): > info
1. John Smith
2. Pizza Shop
   Enter index to show info: > 2
   Organization name: Pizza shop
   Address: Wall St. 1
   Number: +0 (123) 456-789-9999
   Time created: 2018-01-01T00:00
   Time last edit: 2018-01-01T00:00

Enter action (add, remove, edit, count, info, exit): > edit
1. John Smith
2. Pizza Shop
   Select a record: > 1
   Select a field (name, surname, birth, gender, number): > number
   Enter number: > (123) 234 345-456
   The record updated!

Enter action (add, remove, edit, count, info, exit): > info
1. John Smith
2. Pizza Shop
   Select a record: > 1
   Name: John
   Surname: Smith
   Birth date: [no data]
   Gender: [no data]
   Number: (123) 234 345-456
   Time created: 2018-01-01T00:00
   Time last edit: 2018-01-01T00:01

Enter action (add, remove, edit, count, info, exit): > edit
1. John Smith
2. Pizza Shop
   Select a record: > 2
   Select a field (address, number): > address
   Enter address: > Wall St. 7
   The record updated!

Enter action (add, remove, edit, count, info, exit): > info
1. John Smith
2. Pizza Shop
   Enter index to show info: > 2
   Organization name: Pizza shop
   Address: Wall St. 7
   Number: +0 (123) 456-789-9999
   Time created: 2018-01-01T00:00
   Time last edit: 2018-01-01T00:02

Enter action (add, remove, edit, count, info, exit): > exit
```

## Stage 2/4 : [Create a menu](https://hyperskill.org/projects/261/stages/1322/implement)
### Description
Sometimes we need to restrict the ability to change the instance properties. For example, a phone number can't be just any string; it should follow some rules. As you can see [here](https://en.wikipedia.org/wiki/National_conventions_for_writing_telephone_numbers), the phone number format is different for every country, but they all have some elements in common.

So, you should set the property with a phone number to be private and create a getter and setter to this property. The setter should check the value using a regular expression and set the value to the property only if the value satisfies all the rules below:

The phone number should be split into groups using a space or dash. One group is also possible.
Before the first group, there may or may not be a plus symbol.
The first group or the second group can be wrapped in parentheses, but there should be no more than one group that is wrapped in parentheses. There may also be no groups wrapped in parentheses.
A group can contain numbers, uppercase, and lowercase English letters. A group should be at least 2 symbols in length. But the first group may be only one symbol in length.
If you are struggling with a regular expression that checks all of these, you might check the phone number with only String methods.
Also, create getters and setters for the name and surname of the person. Besides, there should be a method hasNumber() that checks if the user has a number. Initially, set the number to be an empty string.

Create a separate method to check the validity of the phone number. This is standalone logic, and potentially it can be used in multiple places of a class. But this is also a method for internal use. Therefore, mark the method as private.

This concept of restricting the usage of a class is called encapsulation. This is a self-documented solution for how to use a class.

In this stage, you should write a program that keeps all the records in a list. You should be able to add, remove, edit the records, and get the number of records. If the user inputs an incorrect phone number, you should reset it as empty. If the number is empty, you should write the string [no number] instead of it.

### Examples
Below is an example of how your output might look. The symbol > represents the user input.

#### Example 1:
```
Enter action (add, remove, edit, count, list, exit): > count
The Phone Book has 0 records.
Enter action (add, remove, edit, count, list, exit): > edit
No records to edit!
Enter action (add, remove, edit, count, list, exit): > remove
No records to remove!
Enter action (add, remove, edit, count, list, exit): > add
Enter the name: > John
Enter the surname: > Smith
Enter the number: > +0 (123) 456-789-ABcd
The record added.
Enter action (add, remove, edit, count, list, exit): > add
Enter the name: > Adam
Enter the surname: > Jones
Enter the number: > +0(123)456-789-9999
Wrong number format!
The record added.
Enter action (add, remove, edit, count, list, exit): > list
1. John Smith, +0 (123) 456-789-ABcd
2. Adam Jones, [no number]
   Enter action (add, remove, edit, count, list, exit): > edit
1. John Smith, +0 (123) 456-789-ABcd
2. Adam Jones, [no number]
   Select a record: > 2
   Select a field (name, surname, number): > number
   Enter number: > (123) 234 345-456
   The record updated!
   Enter action (add, remove, edit, count, list, exit): > list
1. John Smith, +0 (123) 456-789-ABcd
2. Adam Jones, (123) 234 345-456
   Enter action (add, remove, edit, count, list, exit): > remove
1. John Smith, +0 (123) 456-789-ABcd
2. Adam Jones, (123) 234 345-456
   Select a record: > 1
   The record removed!
   Enter action (add, remove, edit, count, list, exit): > list
1. Adam Jones, (123) 234 345-456
   Enter action (add, remove, edit, count, list, exit): > exit
```
#### Example 2:
```
Enter action (add, remove, edit, count, list, exit): > add
Enter the name: > John
Enter the surname: > Smith
Enter the number: > +0 (123) 456-789-ABcd
The record added.
Enter action (add, remove, edit, count, list, exit): > edit
1. John Smith, +0 (123) 456-789-ABcd
   Select a record: > 1
   Select a field (name, surname, number): > number
   Enter number: > ()()
   Wrong number format!
   The record updated!
   Enter action (add, remove, edit, count, list, exit): > list
1. John Smith, [no number]
   Enter action (add, remove, edit, count, list, exit): > exit
```

## Stage 1/4 : [First contact](https://hyperskill.org/projects/261/stages/1321/implement)
### Description
Contacts is a handy app to have. It stores all of your saved contacts. In this project, you will write one yourself. It teaches you to understand and implement the basic principles of OOP.

In the first stage, you should write a program that creates an instance of a class that stores information about one record in the Contacts. One record should contain a name, a surname, and a phone number. You can type them from the keyboard.

You should also create a class representing this app. For now, it should store only one record: a record that you created from the input.

### Example
Below is an example of how your output might look. Lines that start with > represent the user input.
```
Enter the name of the person:
> John
Enter the surname of the person:
> Smith
Enter the number:
> 1-234-567-890

A record created!
A Phone Book with a single record created!
```