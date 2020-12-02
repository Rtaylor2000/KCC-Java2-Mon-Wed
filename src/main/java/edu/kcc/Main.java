/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kcc;

import edu.kcc.animal.Animal;
import edu.kcc.animal.data.AnimalDAO;
import edu.kcc.animal.data.AnimalDAOFactory;
import edu.kcc.ui.UIUtility;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *
 * @author k0519415
 */
public class Main {
    private static final int PORT = 7777;
    private static final String HOST_NAME = "localhost";
    
    private static Animal getAnimalFromServer(String animalName) 
            throws UnknownHostException, IOException {
        Socket socket = new Socket(HOST_NAME, PORT);
        DataInputStream inputStream = 
                new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = 
                new DataOutputStream(socket.getOutputStream());
        outputStream.writeUTF(animalName);
        outputStream.flush();
        
        Animal animal = null;
        
        String id = inputStream.readUTF();
        String name =  inputStream.readUTF();
        String species = inputStream.readUTF();
        String gender = inputStream.readUTF();
        int age = inputStream.readInt();
        Boolean fixed = inputStream.readBoolean();
        BigDecimal weight = BigDecimal.valueOf(inputStream.readDouble());
        String dateAdded = inputStream.readUTF();
        String lastFeeding = inputStream.readUTF();
        
        animal.setId(id);
        animal.setName(name);
        animal.setSpecies(species);
        animal.setGender(gender);
        animal.setAge(age);
        animal.setFixed(fixed);
        animal.setWeight(weight);
        animal.setDateAdded(LocalDate.parse(dateAdded)); 
        animal.setLastFeedingTime(LocalDateTime.parse(lastFeeding));
        
        
        inputStream.close();
        outputStream.close();
        return animal;
    }
    
    private static String getUserInput(String prompt){
        System.out.print(prompt + " ");
        return new Scanner(System.in).nextLine();
    }

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        UIUtility.showMessage("Program starting...");

        String menuTitle = "Main Menu";
        String[] menuOptions = {
            "1) Look up an Animal by Name",
            "2) See Search History",
            "3) Exit"
        };
        String prompt = "Your choice:";
        String errorMessage = "Invalid option.  Please try again.";
        String userChoice;
        AnimalDAO dao = AnimalDAOFactory.getAnimalDAO();

        // Start the primary program logic
        boolean working = true;
        while (working) {
            userChoice = UIUtility.showMenuOptions(menuTitle,
                    prompt, menuOptions);
            switch (userChoice) {
                case "1":
                    //add it here
                    String response;
                    String question = "Enter the animals name or Q to quit:";
                    boolean keepGoing = true;
                    while(keepGoing){
                        response = getUserInput(question);
                        if(response.equalsIgnoreCase("Q")){
                            keepGoing = false;
                        } else {
                            try{
                                Animal animal = getAnimalFromServer(response);
                                System.out.print("Animal: "+animal);
                            } catch(UnknownHostException uhe){
                                System.out.println("ERROR: " + uhe.getMessage());
                                System.out.println("Program terminating!");
                                System.exit(-1);
                            } catch(IOException ioe) {
                                System.out.println("ERROR: " + ioe.getMessage());
                            }
                        }
                    }
                    System.out.println("\nProgram complete.");
                    break;
                case "2":
                    System.out.println("Not set up yet");
                    //new FindOrder().handleTask(dao);
                    break;
                case "3":
                    working = false;
                    break;
                default:
                    UIUtility.showErrorMessage(errorMessage, true);
            }
        }
        UIUtility.showMessage("\nProgram complete.");
    }
    
}
