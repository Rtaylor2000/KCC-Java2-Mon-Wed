/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kcc.animal.taskHandler;

import edu.kcc.animal.Animal;
import edu.kcc.animal.data.AnimalDAO;
import edu.kcc.animal.data.AnimalDataException;
import edu.kcc.animal.data.AnimalDataHandler;
import edu.kcc.ui.UIUtility;
import java.util.List;

/**
 *
 * @author Ryan
 */
public class DeleteAnimal implements AnimalDataHandler{

    @Override
    public void handleTask(AnimalDAO dao) {
        UIUtility.showSectionTitle("Delete an Animal");
        try {
            List<Animal> animals = dao.getAllAnimals();
            String[] menuOptions = new String[animals.size()];
            for (int i = 0; i < menuOptions.length; i++) {
                menuOptions[i] = (i + 1) + ") " + animals.get(i).toString();
            }
            String userChoice = UIUtility.showMenuOptions(
                    "Select a Car to Delete",
                     "Your Choice:",
                     menuOptions);
            try {
                int actualChoice = Integer.parseInt(userChoice) - 1;
                Animal animal = animals.get(actualChoice);
                dao.deleteAnimal(animal);
                UIUtility.showMessage("Animal deleted successfully.");
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                UIUtility.showErrorMessage("No valid Animal selected.", false);
            }

        } catch (AnimalDataException ex) {
            UIUtility.showErrorMessage(ex.getMessage(), true);
        }
        UIUtility.showMessage("\nDelete an Animal complete.");
        UIUtility.pressEnterToContinue();

    }
}
