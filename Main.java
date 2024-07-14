/*
Chase Dallmann
CSC 422 Assignment 1 Release 1
7/7/2024
I Chase Dallmann, hereby certify all this code is written by myself and not copied from any other source.
 */
import java.io.*;
import java.util.*;

public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ArrayList<Pets> pets =new ArrayList<Pets>();
        loadDatabase(pets);
        menu(pets);
        input.close();
    }

    //The menu method that will display the menu for a user to select a choice
    public static void menu(ArrayList<Pets> pets) throws IOException {
        System.out.println("Pet Database Program.\n");
        System.out.print("1) View all pets\n" +
                "2) Add more pets\n" +
                "3) Update an existing pet\n" +
                "4) Remove an existing pet\n" +
                "5) Search pets by name\n" +
                "6) Search pets by age\n" +
                "7) Exit program\n");
        System.out.print("Your choice: ");
        try {
            int userSelection = input.nextInt(); //Taking the users input
            menuLogic(userSelection, pets); //Calling the logic for the selection
        } catch (InputMismatchException ime) {
            System.out.printf("ERROR: Invalid selection please use 1-7 as the options only\n");
            input.nextLine();
            menu(pets);
        }
    }

    //The logic for handling the selections
    public static void menuLogic(int choice, ArrayList<Pets> pets) throws IOException {
        //Using a switch to handle to selection
        switch (choice) {
            case 1: {
                printTable(pets);//Printing the table
                break;
            }
            case 2: {
                if (pets.size() <= 5) {
                    input.nextLine();
                    addPet(pets); //Adding a pet
                } else {
                    System.out.println("ERROR: Database current holds the maximum number of pets");
                }
                break;
            }
            case 3: {
                updatePet(pets); //Updating a pets information
                break;
            }
            case 4: {
                removePet(pets); // Removing a pet from the list
                break;
            }
            case 5: {
                searchPetName(pets); //Searching for pets by name
                break;
            }
            case 6:  {
                searchPetAge(pets); //Searching for pets by age
                break;
            }
            case 7: {
                System.out.println("Goodbye");
                saveDatabase(pets);
                System.exit(0); //Exiting the program
            }
            default: menu(pets); //No selection defaults back to the main menu
        }
        menu(pets); //Going back to the main menu after a selection has taken place
    }

    //A method for adding pets
    public static void addPet(ArrayList<Pets> pets) throws IOException {
        String petInfo = "";
        boolean exitAddPets = false; //Creating a boolean for a while loop
        while (!exitAddPets) { //Looping through until the user types "done"
            int petID = (pets.size()); //PetID is the size of the arrayList
            if (pets.size() > 4 ) {
                System.out.println("ERROR: Unable to add pet. Pet database at maximum size");
                break;
            }
            System.out.println("Please enter the name and the age of the pet: ");
            try {
                petInfo = input.nextLine(); //Getting the pet name from a user input
                if (petInfo.equalsIgnoreCase("done")) {
                    exitAddPets = true; //If done is typed set the boolean to true to break out of the while loop
                } else {
                    try {
                        String[] petInfoParts = petInfo.split("\\s");
                        if (petInfoParts.length >= 3) { //Error handling for 3 or more inputs
                            System.out.printf("ERROR: %s is not a valid entry user has tried to enter %d inputs \n", Arrays.toString(petInfoParts),petInfoParts.length);
                            break;
                        }
                        String petName = petInfoParts[0];
                        int petAge = Integer.parseInt(petInfoParts[1]);
                        if (petAge > 0 && petAge <= 20) { //Making sure the pet is within a realistic age
                            Pets newPet = new Pets(petID, petName, petAge); //Creating a pet object
                            pets.add(newPet); //Adding the pet object to the arrayList
                        } else {
                            System.out.printf("ERROR: %d is not a valid age\n", petAge); //Error handling for invalid age
                        }
                    } catch (InputMismatchException ime) { //Error handling for an invalid name
                        System.out.printf("ERROR: %s is not a valid pet entry\n", petInfo);
                        input.nextLine(); //Clearing the scanner
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.printf("ERROR: %s is not a valid entry please try again.\n", petInfo);
            }
        }
        saveDatabase(pets);
    }

    //Updating a pets information
    public static void updatePet(ArrayList<Pets> pets) throws IOException {
        printTable(pets);
        String petName = "";
        int petAge = -1;
        //Error handling incase for updating a pet
        try {
            System.out.println("What is the ID of the pet you would like to update: ");
            int petID = input.nextInt();
            System.out.println("Please enter the name and the age of the pet: ");
            input.nextLine(); //Clearing the scanner
            String petInfo = input.nextLine(); //Getting the pet name and age from a user input
            if (!petInfo.isEmpty()) {
                String[] petInfoParts = petInfo.split("\\s");
                if (petInfoParts.length < 3) {
                    petName = petInfoParts[0]; // Getting the petName
                    petAge = Integer.parseInt(petInfoParts[1]); //Getting the petAge
                    String oldName = pets.get(petID).getName(); //Previous Name
                    int oldAge = pets.get(petID).getAge(); //Previous Age
                    pets.get(petID).setName(petName); //Setting the new Name
                    pets.get(petID).setAge(petAge); //Setting the new Age
                    System.out.println("UPDATE SUCCESSFUL: " + oldName + " " + oldAge + " has been changed to " + petName + " "
                            + petAge); //Update success message
                } else {
                    System.out.printf("ERROR: Invalid number of entries detected %d\n", petInfoParts.length); //Invalid number of entries
                }
            }
        } catch (NumberFormatException nfe) {
            System.out.printf("ERROR: Unable to update pet properly %d is an invalid age \n", petAge); //If the user enters a name correct but not an age correctly
            input.nextLine();
        } catch (InputMismatchException ime) {
            System.out.printf("ERROR: Invalid selection. The selection must be between 0 and %d\n", pets.size()); //If the user selects an ID that doesnt exist
            input.nextLine();
        }
        saveDatabase(pets);
    }

    //Removing a pet from the ArrayList
    public static void removePet(ArrayList<Pets> pets) throws IOException {
        int petID = -1;
        if (pets.isEmpty()) {
            System.out.println("No pets in array");
        } else {
        printTable(pets);
        System.out.println("What is the ID of the pet you would like to remove: ");
        try {
            petID = input.nextInt();
            String petName = pets.get(petID).getName();
            int petAge = pets.get(petID).getAge();
            pets.remove(petID);
            System.out.println("The pet " + petName + " " + petAge + " has been removed from the list of pets.");
            //Decrementing all the ID's after the pet that was removed
            for (Pets pet : pets) {
                if ((petID < pet.getID())) {
                    pet.setID(pet.getID() - 1);
                }
            }
        } catch (InputMismatchException ime) {
            System.out.println("ERROR: Invalid ID entered");
            input.nextLine();
        } catch (IndexOutOfBoundsException iob) {
            System.out.printf("ERROR: Invalid Index selected %d\n", petID);
        }
        }
        saveDatabase(pets);
    }


    //Printing the table/arrayList
    public static void printTable(ArrayList<Pets> pets) {
        printHeader(); //Printing the header
        for (Pets pet : pets) { //Printing each pet
            System.out.printf("| %-3s | %-10s | %-4s |\n", pet.getID(), pet.getName(),pet.getAge());
        }
        printFooter(); //Printing the footer
        System.out.printf("Rows in set: %d\n",pets.size()); //Printing the size of the arrayList/# of rows
    }

    //Overloading this method to handle a temporary arrayList as well
    public static void printTable(ArrayList<Pets> pets,ArrayList<Pets> searchArray) {
        printHeader(); //Printing the header
        for (Pets pet : searchArray) { //Printing each pet
            System.out.printf("| %-3s | %-10s | %-4s |\n", pet.getID(), pet.getName(),pet.getAge());
        }
        printFooter(); //Printing the footer
        System.out.printf("Rows in set: %d\n",searchArray.size()); //Printing the size of the arrayList/# of rows
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
    public static void searchPetAge(ArrayList<Pets> pets) throws IOException {
        ArrayList<Pets> petSearch = new ArrayList<>();
        System.out.println("Enter age to search: ");
        try {
            int userSearch = input.nextInt();
            if (userSearch > 0 && userSearch <= 20) {
                for (Pets pet : pets) {
                    if (pet.getAge() == userSearch) {
                        petSearch.add(pet);
                    }
                }
                printTable(pets, petSearch);
            } else {
                System.out.printf("ERROR: Invalid age entered %d\n", userSearch);
            }
        } catch (InputMismatchException ime) {
            System.out.println("ERROR: Invalid input. You must enter a number between 1-20 to search for.");
            input.nextLine();
            menu(pets);
        }
    }

    //Used to load the objects from the JSON database
    public static ArrayList<Pets> loadDatabase(ArrayList<Pets> pets) throws IOException, ClassNotFoundException {
        int petCount = 0;
        File file = new File("Database.JSON"); //Checking if a JSON file exists
        if (!file.exists()) {
            System.out.printf("File not found %s creating new file.\n", file.getName()); //Create the file if it doesnt exist
            return pets;
        }
        FileInputStream fis = new FileInputStream("Database.JSON"); //Creating the file stream with the JSON
        ObjectInputStream ois = new ObjectInputStream(fis); //Creating an object stream to add objects to the file
        try {
            while(true) { //Looping through until the EOF Exception is caught when the end of the file is reached
                if (petCount > 5) {
                    throw new RuntimeException("ERROR: Database File too large. The database file has more then 5 lines"); //Covering the case of the database having more then 5 entries
                }
                Pets loadedPet = (Pets) ois.readObject(); //Creating a new Pets object from the object
                pets.add(loadedPet); //Adding the new Pets object to the pets ArrayList
                petCount++;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (EOFException eof) {
        } finally {
            ois.close();
            fis.close();
        }
        return pets;
    }

    //Used to save the Objects to the JSON database
    public static void saveDatabase(ArrayList<Pets> pets) throws IOException {
        FileOutputStream fos = new FileOutputStream("Database.JSON"); //Creating a file output stream from the JSON
        ObjectOutputStream oos = new ObjectOutputStream(fos); //Creating an object stream to save the Pets objects
        //Saving each pet object in the pets array to the JSON as a serialized object
        for (Pets pet: pets) {
            oos.writeObject(pet);
        }
        oos.flush();
        oos.close();
        fos.close();
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
