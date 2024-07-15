# [contacts-kotlin](https://github.com/javapda/contacts-kotlin)
* based on the Hyperskill [Contacts (Kotlin)](https://hyperskill.org/projects/261/) project
* project [contacts-kotlin](https://github.com/javapda/contacts-kotlin) on github

# resources
* [regex tester](https://www.freeformatter.com/regex-tester.html)

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