/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kcc;

import edu.kcc.animal.Animal;
import edu.kcc.animal.data.AnimalDAO;
import edu.kcc.animal.data.AnimalDAOFactory;
import edu.kcc.animal.data.AnimalDataException;
import edu.kcc.animal.data.AnimalHistoryXML;
import edu.kcc.ui.UIUtility;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;
import org.w3c.dom.DocumentFragment;

/**
 *
 * @author k0519415
 */
public class Main {
    private static final int PORT = 7777;
    private static final String HOST_NAME = "localhost";
    public static final AnimalHistoryXML xml = new AnimalHistoryXML();
    
    private static Animal getAnimalFromServer(String animalName) 
            throws UnknownHostException, IOException {
        Socket socket = new Socket(HOST_NAME, PORT);
        DataInputStream inputStream = 
                new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = 
                new DataOutputStream(socket.getOutputStream());
        outputStream.writeUTF(animalName);
        outputStream.flush();
        
        Animal animal = new Animal();
        
        String id = inputStream.readUTF();
        //System.out.println("id: "+id);
        String name =  inputStream.readUTF();
        //System.out.println("name: "+name);
        String species = inputStream.readUTF();
        //System.out.println("species: "+species);
        String gender = inputStream.readUTF();
        //System.out.println("gender: "+gender);
        int age = inputStream.readInt();
        //System.out.println("age: "+age);
        Boolean fixed = inputStream.readBoolean();
        //System.out.println("fixed: "+fixed);
        BigDecimal weight = BigDecimal.valueOf(inputStream.readDouble());
        //System.out.println("weight: "+weight);
        String dateAdded = inputStream.readUTF();
        //System.out.println("dateAdded: "+dateAdded);
        String lastFeeding = inputStream.readUTF();
        //System.out.println("lastFeeding: "+lastFeeding);
        
        animal.setId(id);
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
        //AnimalDAO dao = AnimalDAOFactory.getAnimalDAO();

        // Start the primary program logic
        boolean working = true;
        while (working) {
            userChoice = UIUtility.showMenuOptions(menuTitle,
                    prompt, menuOptions);
            switch (userChoice) {
                case "1":
                    //add it here
                    String response;
                    String question = "\nEnter the animals name or Q to quit:";
                    boolean keepGoing = true;
                    while(keepGoing){
                        response = getUserInput(question);
                        if(response.equalsIgnoreCase("Q")){
                            keepGoing = false;
                        } else {
                            try{
                                Animal animal = getAnimalFromServer(response);
                                System.out.print(animal.toString());
                                xml.createHistoryRecord(animal);
                                   
                            } catch(UnknownHostException uhe){
                                System.out.println("ERROR: " + uhe.getMessage());
                                System.out.println("Program terminating!");
                                System.exit(-1);
                            } catch(IOException ioe) {
                                System.out.println("ERROR: " + ioe.getMessage());
                            } catch(AnimalDataException ex){
                                System.out.println("ERROR: "+ex.getMessage());
                            }
                        }
                    }
                    System.out.println("\nProgram complete.");
                    break;
                case "2":
                    try{
                        Map<LocalDateTime, Animal> history = xml.getSearchHistory();
                        if(history.size() == 0){
                            System.out.println("There is nothing in "
                                    + "your search history.");
                            System.out.println("Pleas look for an animal.");
                        }
                        else{
                            for (Map.Entry<LocalDateTime, Animal> entry : history.entrySet()) {
                                System.out.println("Search time: " 
                                        + entry.getKey() + " Search result: " 
                                        + entry.getValue());
                            }
                        }
                    } catch(AnimalDataException ex){
                        System.out.println("ERROR: "+ex.getMessage());
                    }
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
