package edu.kcc.taskHandler;

import edu.kcc.animal.Animal;
import edu.kcc.animal.data.AnimalDAO;
import edu.kcc.animal.data.AnimalDataHandler;
import edu.kcc.animal.data.AnimalDataException;
import edu.kcc.ui.UIUtility;
import java.util.List;

/**
 *
 * @author Melissa Isaacson
 */
public class ShowAllAnimals implements AnimalDataHandler {
    @Override
    public void handleTask(AnimalDAO dao) {
        UIUtility.showSectionTitle("Show All Animals");

        try {
            List<Animal> animals = dao.getAllAnimals();
            for (Animal animal : animals) {
                UIUtility.showMessage("\t" + animal);
            }
        } catch (AnimalDataException ex) {
            UIUtility.showErrorMessage(ex.getMessage(), true);
        }

        UIUtility.showMessage("\nShow All Animals complete.");
        UIUtility.pressEnterToContinue();

    }

    public List<Animal> getAllAnimals(AnimalDAO dao) throws AnimalDataException{
        return dao.getAllAnimals();
    }
}
