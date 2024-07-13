/*
Chase Dallmann
CSC 422 Assignment 1 Release 1
7/7/2024
I Chase Dallmann, hereby certify all this code is written by myself and not copied from any other source.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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
        int userSelection = input.nextInt(); //Taking the users input
        menuLogic(userSelection, pets); //Calling the logic for the selection
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
    public static void addPet(ArrayList<Pets> pets) {
        int petCount = 0;
        input.nextLine(); //Clearing the scanner
        boolean exitAddPets = false; //Creating a boolean for a while loop
        while (!exitAddPets) { //Looping through until the user types "done"
            int petID = (pets.size()); //PetID is the size of the arrayList
            System.out.println("Please enter the name and the age of the pet: ");
            String petInfo = input.nextLine(); //Getting the pet name from a user input
            if (petInfo.equalsIgnoreCase("done") || petCount == 5) {
                exitAddPets = true; //If done is typed set the boolean to true to break out of the while loop
            } else {
                try {
                    String[] petInfoParts = petInfo.split("\\s");
                    String petName = petInfoParts[0];
                    int petAge = Integer.parseInt(petInfoParts[1]);
                    if (petAge > 0 && petAge <= 20) { //Making sure the pet is within a realistic age
                        Pets newPet = new Pets(petID, petName, petAge); //Creating a pet object
                        pets.add(newPet); //Adding the pet object to the arrayList
                        petCount++;
                    } else {
                        System.out.printf("ERROR: %d is not a valid age\n", petAge);
                    }
                } catch (Exception e) { //Error handling
                    System.out.printf("ERROR: %s invalid entry\n", e);
                }
            }
        }
    }

    //Updating a pets information
    public static void updatePet(ArrayList<Pets> pets) {
        printTable(pets);
        //Error handling incase for updating a pet
        try {
            System.out.println("What is the ID of the pet you would like to update: ");
            int petID = input.nextInt();
            System.out.println("Please enter the name and the age of the pet: ");
            input.nextLine(); //Clearing the scanner
            String petInfo = input.nextLine(); //Getting the pet name from a user input
            String[] petInfoParts = petInfo.split("\\s");
            String petName = petInfoParts[0];
            int petAge = Integer.parseInt(petInfoParts[1]);
            String oldName = pets.get(petID).getName(); //Previous Name
            int oldAge = pets.get(petID).getAge(); //Previous Age
            pets.get(petID).setName(petName); //Setting the new Name
            pets.get(petID).setAge(petAge); //Setting the new Age
            System.out.println("UPDATE SUCCESSFUL: " + oldName + " " + oldAge + " has been changed to " + petName + " "
            + petAge);
        } catch (Exception e) {
            System.out.println("ERROR: Unable to update pet properly " + e);
        }
    }

    //Removing a pet from the ArrayList
    public static void removePet(ArrayList<Pets> pets) throws IOException {
        if (pets.isEmpty()) {
            System.out.println("No pets in array");
        } else {
        printTable(pets);
        System.out.println("What is the ID of the pet you would like to remove: ");
        int petID =  input.nextInt();
        String petName = pets.get(petID).getName();
        int petAge = pets.get(petID).getAge();
        pets.remove(petID);
        System.out.println("The pet " + petName + " " + petAge + " has been removed from the list of pets.");
        //Decrementing all the ID's after the pet that was removed
        for (Pets pet: pets) {
            if ((petID < pet.getID())) {
                pet.setID(pet.getID() - 1);
                }
            }
        }
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

    public static ArrayList<Pets> loadDatabase(ArrayList<Pets> pets) throws IOException, ClassNotFoundException {
        int petCount = 0;
        File file = new File("Database.JSON");
        if (!file.exists()) {
            System.out.printf("File not found %s creating new file.\n", file.getName());
            return pets;
        }
        FileInputStream fis = new FileInputStream("Database.JSON");
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            while(true) {
                if (petCount > 5) {
                    throw new RuntimeException("ERROR: Database File too large. The database file has more then 5 lines");
                }
                Pets loadedPet = (Pets) ois.readObject();
                pets.add(loadedPet);
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

    public static void saveDatabase(ArrayList<Pets> pets) throws IOException {
        FileOutputStream fos = new FileOutputStream("Database.JSON",true);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
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
