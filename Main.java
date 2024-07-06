/*
Chase Dallmann
CSC 422 Assignment 1 Release 1
7/5/2024
I Chase Dallmann, hereby certify all this code is written by myself and not copied from any other source.
 */
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Pets> pets = new ArrayList<>();
        menu(pets);
    }

    //The menu method that will display the menu for a user to select a choice
    public static void menu(ArrayList<Pets> pets) {
        System.out.println("Pet Database Program.\n");
        System.out.print("1) View all pets\n" +
                "2) Add more pets\n" +
                "3) Update an existing pet\n" +
                "4) Remove an existing pet\n" +
                "5) Search pets by name\n" +
                "6) Search pets by age\n" +
                "7) Exit program\n");
        System.out.print("Your choice: ");
        int userSelection = input.nextInt(); //Taking the users input
        menuLogic(userSelection, pets); //Calling the logic for the selection
    }

    //The logic for handling the selections
    public static void menuLogic(int choice, ArrayList<Pets> pets) {
        //Using a switch to handle to selection
        switch (choice) {
            case 1: printTable(pets); //Printing the table
            case 2: addPet(pets); //Adding a pet
            case 3: System.out.println("Placeholder for build 3"); //TODO Add logic
            case 4: System.out.println("Placeholder for build 3"); //TODO Add logic
            case 5: searchPetName(pets); //Searching for pets by name
            case 6: searchPetAge(pets); //Searching for pets by age
            case 7: System.exit(0); //Exiting the program
            default: menu(pets); //No selection defaults back to the main menu
        }
        menu(pets); //Going back to the main menu after a selection has taken place
    }

    //A method for adding pets
    public static void addPet(ArrayList<Pets> pets) {
        boolean exitAddPets = false; //Creating a boolean for a while loop
        while (!exitAddPets) { //Looping through until the user types "done"
            int petID = (pets.size()); //PetID is the size of the arrayList
            System.out.println("What is the name of the pet?: ");
            String petName = input.next(); //Getting the pet name from a user input
            if (petName.equalsIgnoreCase("done")) {
                exitAddPets = true; //If done is typed set the boolean to true to break out of the while loop
            } else {
                try {
                    System.out.println("What is the pets age?: ");
                    int petAge = input.nextInt();
                    if (petAge > 0 && petAge < 150) { //Making sure the pet is within a realistic age
                        Pets newPet = new Pets(petID, petName, petAge); //Creating a pet object
                        pets.add(newPet); //Adding the pet object to the arrayList
                    } else {
                        System.out.println("ERROR: You must enter a number between 1 and 150 for age");
                        input.nextLine();
                    }
                } catch (InputMismatchException e) { //Error handling
                    System.out.println("ERROR: You must enter a number between 1 and 150 for age");
                    input.nextLine();
                }
            }
        }
        menu(pets); //Returning to the main menu
    }

    /*
    public static void updatePet(ArrayList<Pets> pets) {
        printTable(pets);
        System.out.println("What is the ID of the pet you would like to update: ");
        int petID = input.nextInt();
    }
    */

    //Printing the table/arrayList
    public static void printTable(ArrayList<Pets> pets) {
        printHeader(); //Printing the header
        for (Pets pet : pets) { //Printing each pet
            System.out.printf("| %-3s | %-10s | %-4s |\n", pet.getID(), pet.getName(),pet.getAge());
        }
        printFooter(); //Printing the footer
        System.out.printf("Rows in set: %d\n",pets.size()); //Printing the size of the arrayList/# of rows
        menu(pets); //Returning to the main menu
    }

    //Overloading this method to handle a temporary arrayList as well
    public static void printTable(ArrayList<Pets> pets,ArrayList<Pets> searchArray) {
        printHeader(); //Printing the header
        for (Pets pet : searchArray) { //Printing each pet
            System.out.printf("| %-3s | %-10s | %-4s |\n", pet.getID(), pet.getName(),pet.getAge());
        }
        printFooter(); //Printing the footer
        System.out.printf("Rows in set: %d\n",searchArray.size()); //Printing the size of the arrayList/# of rows
        menu(pets); //Returning to the main menu
    }

    //A method to print the header
    public static void printHeader() {
        System.out.println("+--------------------------+");
        System.out.printf("| %-3s | %-10s | %-4s | \n", "ID","NAME", "AGE");
        System.out.println("+--------------------------+");
    }

    //A method to print the footer
    public static void printFooter() {
        System.out.println("+--------------------------+");
    }

    //Searching for pets by name
    public static void searchPetName(ArrayList<Pets> pets) {
        ArrayList<Pets> petSearch = new ArrayList<>();
        System.out.println("Enter name to search: ");
        String userSearch = input.next();
        for (Pets pet : pets) {
            if (pet.getName().equalsIgnoreCase(userSearch)) {
                petSearch.add(pet);
            }
        }
        printTable(pets, petSearch);
    }

    //Searching for pets by age
    public static void searchPetAge(ArrayList<Pets> pets) {
        ArrayList<Pets> petSearch = new ArrayList<>();
        System.out.println("Enter age to search: ");
        int userSearch = input.nextInt();
        for (Pets pet : pets) {
            if (pet.getAge() == userSearch) {
                petSearch.add(pet);
            }
        }
        printTable(pets, petSearch);
    }
}


/*
Sources:
https://stackoverflow.com/questions/10604125/how-can-i-clear-the-scanner-buffer-in-java
https://www.w3schools.com/java/java_try_catch.asp
https://www.geeksforgeeks.org/format-specifiers-in-java/#
https://www.geeksforgeeks.org/difference-between-next-and-nextline-methods-in-java/
https://www.w3schools.com/java/java_user_input.asp
https://www.baeldung.com/java-printstream-printf
https://www.geeksforgeeks.org/arraylist-in-java/
 */
