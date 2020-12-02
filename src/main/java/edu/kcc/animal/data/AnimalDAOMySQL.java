/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kcc.animal.data;

import edu.kcc.animal.Animal;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinayak Deshpande
 */
public class AnimalDAOMySQL implements AnimalDAO {

    private static ArrayList<Animal> animals;

    private Connection buildConnection() throws SQLException { //problem with connecting
        String databaseUrl = "localhost";
        String databasePort = "3307";
        String databaseName = "animal";
        String userName = "root";
        String password = "password";

        String connectionString = "jdbc:mysql://" + databaseUrl + ":"
                + databasePort + "/" + databaseName + "?"
                + "user=" + userName + "&"
                + "password=" + password + "&"
                + "useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(connectionString);
    }

    private void readFromDataBase() throws AnimalDataException {

        try (Connection conn = buildConnection()) {
            if (conn.isValid(2)) {
                animals = new ArrayList<>();
                CallableStatement callableStatement
                        = conn.prepareCall("Call sp_get_all_Animal();");
                ResultSet resultSet = callableStatement.executeQuery();
                String id;
                String name;
                String species;
                String gender;
                int age;
                boolean fixed;
                int legs;
                BigDecimal weight;
                LocalDate dateAdded;
                LocalDateTime lastFeedingTime;
                while (resultSet.next()) {
                    id = resultSet.getString("Id");
                    name = resultSet.getString("Name");
                    species = resultSet.getString("Species");
                    gender = resultSet.getString("Gender");
                    age = resultSet.getInt("Age");
                    fixed = resultSet.getBoolean("Fixed");
                    legs = resultSet.getInt("Legs");
                    weight = resultSet.getBigDecimal("Weight");
                    dateAdded
                            = resultSet.getObject("Date_Added", LocalDate.class);
                    lastFeedingTime
                            = resultSet.getObject("Last_Feeding_Time",
                                    LocalDateTime.class);
                    animals.add(new Animal(
                            id,
                            name,
                            species,
                            gender,
                            age,
                            fixed,
                            legs,
                            weight,
                            dateAdded,
                            lastFeedingTime
                    ));
                }
                resultSet.close();
                callableStatement.close();
            }
        } catch (Exception ex) {
            System.out.println("Exception Message: " + ex.getMessage());
            if (ex instanceof SQLException) {
                SQLException sqlException = (SQLException) ex;
                System.out.println("Error Code: " + sqlException.getErrorCode());
                System.out.println("SQL State: " + sqlException.getSQLState());
            }
        }
    }

    private void verifyAnimalList() throws AnimalDataException {
        if (null == animals) {
            readFromDataBase();
        }
    }
    
    @Override
    public Animal getAnimalByAnimalName(String name) throws AnimalDataException {
        Animal animal = null;
        verifyAnimalList();
        for(Animal tempAnimal : animals) {
            if(tempAnimal.getName().equals(name)) {
                animal = tempAnimal;
                break;
            }
        
        }
        return animal;
        
    }

    @Override
    public ArrayList<Animal> getAllAnimals() throws AnimalDataException {
        verifyAnimalList();
        return animals;
    }

    @Override
    public void createAnimal(Animal animal) throws AnimalDataException {
        verifyAnimalList();
        Animal checkAnimal = getAnimalByAnimalName(animal.getName());
        if (null != checkAnimal) {
            throw new AnimalDataException("Animal Ids must be unique...");
        }
        animals.add(animal);
        try {
            Connection conn = buildConnection();
            CallableStatement callableStatement
                    = conn.prepareCall("CAll sp_add_Animal(?,?,?,?,?,?,?,?);");
            callableStatement.setString("Id", animal.getId());
            callableStatement.setString("Name", animal.getName());
            callableStatement.setString("Species", animal.getSpecies());
            callableStatement.setString("Gender", animal.getGender());
            callableStatement.setInt("Age", animal.getAge());
            callableStatement.setBoolean("Fixed", animal.getFixed());
            callableStatement.setInt("Legs", animal.getLegs());
            callableStatement.setBigDecimal("Weight", animal.getWeight());
            callableStatement.setObject("Date_Added", animal.getDateAdded());
            callableStatement.setObject("Last_Feeding_Time",
                    animal.getLastFeedingTime());
            
            callableStatement.execute();
            callableStatement.close();
            conn.close();
        
        } catch (SQLException ex) {
            throw new AnimalDataException(ex);
        } 
        
    }
    
    @Override
    public void updateAnimal(Animal original, Animal updated) throws AnimalDataException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAnimal(Animal animal) throws AnimalDataException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAnimal(String animalName) throws AnimalDataException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
