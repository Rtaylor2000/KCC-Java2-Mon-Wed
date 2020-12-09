package edu.kcc.animal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Animal implements Comparable<Animal>{

    private String id;
    private static List<String> idList = new ArrayList<>();
    private String name;
    private String species;
    private String gender;
    private int age;
    private boolean fixed;
    private int legs;
    private BigDecimal weight;
    private LocalDate dateAdded;
    private LocalDateTime lastFeedingTime;

    public Animal(String id, String name, String species, String gender, int age, boolean fixed,
            int legs, BigDecimal weight, LocalDate dateAdded, 
            LocalDateTime lastFeedingTime){
        setId(id);
        setName(name);
        setSpecies(species);
        setGender(gender);
        setAge(age);
        setFixed(fixed);
        setLegs(legs);
        setWeight(weight);
        setDateAdded(dateAdded);
        setLastFeedingTime(lastFeedingTime);
    }
    
    public Animal() {
        id = "0";
        idList.add(id);
        name = "Unknown";
        species = "cat";
        gender = "Unknown";
        age = 0;
        fixed = false;
        legs = 4;
        weight = BigDecimal.valueOf(0);
        dateAdded = LocalDate.now();
        lastFeedingTime = LocalDateTime.now().withNano(0).withSecond(0);
    }
    
    public String getId(){
        return id;
    }

    public void setId(String id) {
        //idValidator(id);
        this.id = id;
    }
    
    private void idValidator(String id) {
        if (idList.contains(id)){
            throw new IllegalArgumentException("Id already in idList.");
        }
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies(){
        return species;
    }
    
    public void setSpecies(String species){
        //speciesValidator(species);
        this.species = species;
    }
  
    private void speciesValidator(String species) {
        if(!species.equals("cat") && !species.equals("dog")){
            throw new IllegalArgumentException("The species of must be either cat or dog");
        } 
    }
    
    public String getGender(){
        return gender;
    }
    
    public void setGender(String gender) {
        //genderValidator(gender);
        this.gender = gender;
    }
    
    // TODO: Need genderValidator method - Only allow male and female. Only allow it to change if it's "Unknown".
    private void genderValidator(String gender) {
            if(gender.toLowerCase().compareTo("female") == 0){
                    this.gender = gender;
            }
            else if(gender.toLowerCase().compareTo("male") == 0){
                    this.gender = gender;
            }
            else{
                throw new IllegalArgumentException("Please enter 'male' or 'female' for "
                                        + "gender.");
            }
    }
    
    public int getAge(){
        return age;
    }
    
    public void setAge(int animalAge){
        //ageValidator(age);
        this.age = animalAge;
    }

    private void ageValidator(int age) {
        if (age > 100 || age < 0) {
            throw new IllegalArgumentException("Invalid Animal age.");
        }
        
    }
    
    public boolean getFixed() {    
        return fixed;
    }

    public void setFixed(boolean fixed) {
        //fixedValidator(fixed);
        this.fixed = fixed;
    }
    
    private void fixedValidator(boolean fixed) {
      if((this.fixed == true && fixed == false) || (this.fixed == true && fixed == true)){
            throw new IllegalArgumentException("Animal is already fixed.");
        }
    }
    
    public int getLegs(){
        return legs;
    }
    
    public void setLegs(int legs) {
        //legsValidator(legs);
        this.legs = legs;
    }
    
    public void legsValidator(int legs)
    {
        if (legs>4 || legs<0)
        {
            throw new IllegalArgumentException("Legs are only allowed to be between 0 and 4");
        }
    }
    
    public BigDecimal getWeight() {
        return weight;
    }
    
    public void  setWeight(BigDecimal lb){
        //weightValidator(weight);
        this.weight = lb;
    }
    
    public void weightValidator(BigDecimal weight) {
        BigDecimal max = new BigDecimal("1000.0");
        BigDecimal min = new BigDecimal("0.0");
        if (weight.compareTo(max) == 1 || weight.compareTo(min) == -1) {
            throw new IllegalArgumentException("Invalid weight. Acceptable "
                    + "weight ranges include 0.0-1000.0.");
        }
    }

    public LocalDate getDateAdded(){
        return dateAdded;
    }

    public void setDateAdded(LocalDate ldt){
        //dateValidator(ldt);
        dateAdded = ldt;
    }
    
    public void dateValidator(LocalDate dateAdded) {
        LocalDate oneWeekAgo = LocalDate.now().minusDays(7);
        if(dateAdded.isBefore(oneWeekAgo)){
            throw new IllegalArgumentException(dateAdded + " is more than"
                    + " one week in the past");
        }
        else if( LocalDate.now().isBefore(dateAdded)){
            throw new IllegalArgumentException(dateAdded + " is a date in the "
                    + "future");
        }
    }
    
    public LocalDateTime getLastFeedingTime(){
        return lastFeedingTime;
    }

    public void setLastFeedingTime(LocalDateTime ldt) {
        //feedingValidator(ldt);
        lastFeedingTime = ldt;
    }
    
    public void feedingValidator(LocalDateTime ldt){
        LocalDateTime twoDaysAgo = LocalDateTime.now().minusDays(2);
        if(ldt.isBefore(twoDaysAgo)){
            throw new IllegalArgumentException(ldt + " is more than"
                    + " two days in the past");
        } else if (LocalDateTime.now().isBefore(ldt)){
            throw new IllegalArgumentException(ldt + " is in the future.");
        }
    }

    @Override
    public String toString() {
        return "Animal {name: " + name + ", species: " + species + ", gender: "
                + gender + ", age: " + age + ", \nfixed: " + fixed + 
                ", legs: " + legs + ", weight: " + weight + 
                ", date added: " + dateAdded + 
                ", \nlast feeding time: " + lastFeedingTime +"}";
    }
    
    @Override
    public int compareTo(Animal other) {
        if (this.species.compareTo(other.species) != 0) {
            return this.species.compareTo(other.species);
        }
        else {
            return this.name.compareTo(other.name);
        }
    }
    
}