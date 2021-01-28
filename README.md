# JavaFX
I made an address book program using javaFX in this project.

The major steps I followed to solve the problem:

 First of all, I created 3 classes in total as Main, Person and FileOperations in my project. 
STEP 1 - I defined text fields, labels, button and size of specific fields.
STEP 2 - I created user interfaces using labels, buttons and text fields.
STEP 3 - I created a class called FileOperations and used Random Access File for reading and writing an address.
STEP 4 - I created the Person class to keep all data in an array.
STEP 5 - I have defined all the necessary functions in the main class as follows:
writeAddressToFile(); -> to write information to file.
readFileFillArray(); -> to read data from file.
readFileByPos(); -> to read a record at the specified position.
traverseArray(); -> to traverse array elements.
Update_Person(); -> to update the address information.
cleanTextFields(); -> to clear textFields.
STEP 6 - I defined all our onclick events and completed the program 

The functional features of the program are as follows:

1) You can save the user data you have entered with the Add button. Each user information will be recorded sequentially and while entering a user information, you can leave the text fields you want blank or fill them all.
2) You can browse sequential user information using First, Next, Previous, Last, SearchByID buttons. You must enter an id in the Search/Update ID text field to search by SearchByID. It will give a warning if you don't enter id it or enter an invalid id.
3) To update address information with UpdateByID, you must first enter a valid ID number in the Search/Update ID text field. Then the address of the person will be updated with the data you enter in the fields.
4) You can clear all text fields by clicking Clean textFields.
