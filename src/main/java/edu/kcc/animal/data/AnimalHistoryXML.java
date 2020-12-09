/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kcc.animal.data;

import edu.kcc.animal.Animal;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Ryan
 */
public class AnimalHistoryXML {
    private static final String FILE_NAME = "animalHistory.xml";
    Map<LocalDateTime, Animal> lookupHistory;
    
    private void readFromFile() throws AnimalDataException {
        try(InputStream inputStream = new FileInputStream(FILE_NAME)){
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
 
            Document document = builder.parse(inputStream);
            
            NodeList animalNodeList = document.getElementsByTagName("animal");
            lookupHistory = new HashMap();
            for(int i = 0; i < animalNodeList.getLength(); i++) {
                Node currentAnimalNode = animalNodeList.item(i);
                NamedNodeMap animalAttributeMap = currentAnimalNode.getAttributes();
                Attr attr = (Attr)animalAttributeMap.getNamedItem("lookupTime");
                LocalDateTime lookupTime = LocalDateTime.parse(attr.getValue());
                lookupHistory.put(lookupTime, buildAnimalFromNode(currentAnimalNode));
            }
        }
        catch (Exception ex) {
            throw new AnimalDataException(ex);
        }
    }
    
    private static Animal buildAnimalFromNode(Node animalNode) {
        Animal newAnimal = new Animal();
    
        NodeList animalDataNodeList = animalNode.getChildNodes();
            for(int i = 0; i < animalDataNodeList.getLength(); i++) {
                Node dataNode = animalDataNodeList.item(i);
                if(dataNode instanceof Element) {
                    Element dataElement = (Element)dataNode;
                    switch(dataElement.getTagName()) {
                        case "id":
                            String id = dataElement.getTextContent();
                            newAnimal.setId(id);
                            break;
                        case "name":
                            String name = dataElement.getTextContent();
                            newAnimal.setName(name);
                            break;
                        case "species":
                            String species = dataElement.getTextContent();
                            newAnimal.setSpecies(species);
                            break;
                        case "gender":
                            String gender = dataElement.getTextContent();
                            newAnimal.setGender(gender);
                            break;
                        case "age":
                            int age = Integer.parseInt(dataElement.getTextContent());
                            newAnimal.setAge(age);
                            break;
                        case "fixed":
                            boolean fixed = Boolean.parseBoolean(dataElement.getTextContent());
                            newAnimal.setFixed(fixed);
                            break;
                        case "legs":
                            int legs = Integer.parseInt(dataElement.getTextContent());
                            newAnimal.setLegs(legs);
                            break;
                        case "weight":
                            BigDecimal weight = new BigDecimal(dataElement.getTextContent());
                            newAnimal.setWeight(weight);
                            break;
                        case "dateAdded":
                            LocalDate dateAdded = LocalDate.parse(dataElement.getTextContent());
                            newAnimal.setDateAdded(dateAdded);
                            break;
                        case "lastFeedingTime":
                            LocalDateTime lastFeedingTime = LocalDateTime.parse(dataElement.getTextContent());
                            newAnimal.setLastFeedingTime(lastFeedingTime);
                            break;
                        default:
                            break;
                    }
                }
            }
        return newAnimal;
    }
    
    private void saveToFile() throws AnimalDataException {
        try(FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            
            Element rootElement = document.createElement("animals");
            document.appendChild(rootElement);
            
            for (Map.Entry<LocalDateTime, Animal> entry : lookupHistory.entrySet()) {
                LocalDateTime lookupTime = entry.getKey();
                Animal currentAnimal = entry.getValue();
                DocumentFragment animalFragment = buildAnimalFragment(document, 
                        lookupTime, currentAnimal);
                rootElement.appendChild(animalFragment);
                //System.out.println("The capital of " + entry.getKey() + " is " + entry.getValue());
            }
            
            DOMSource source = new DOMSource(document);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.transform(source, new StreamResult(fos));
        } catch(Exception ex) {
            throw new AnimalDataException(ex);
        }
    }
    
    private static DocumentFragment buildAnimalFragment(Document document, 
            LocalDateTime lookupTime, Animal animal) {
        DocumentFragment animalFragment = document.createDocumentFragment();
        
        Element animalElement = document.createElement("animal");
        animalElement.setAttribute("lookupTime", lookupTime.toString());
        
        Element makeElement = document.createElement("id");
        makeElement.setTextContent(animal.getId());
        animalElement.appendChild(makeElement);
        
        Element nameElement = document.createElement("name");
        nameElement.setTextContent(animal.getName());
        animalElement.appendChild(nameElement);
        
        Element speciesElement = document.createElement("species");
        speciesElement.setTextContent(animal.getSpecies());
        animalElement.appendChild(speciesElement);
        
        Element genderElement = document.createElement("gender");
        genderElement.setTextContent(animal.getGender());
        animalElement.appendChild(genderElement);
        
        Element ageElement = document.createElement("age");
        ageElement.setTextContent(Integer.toString(animal.getAge()));
        animalElement.appendChild(ageElement);
        
        Element fixedElement = document.createElement("fixed");
        fixedElement.setTextContent(Boolean.toString(animal.getFixed()));
        animalElement.appendChild(fixedElement);
        
        Element legsElement = document.createElement("legs");
        legsElement.setTextContent(Integer.toString(animal.getLegs()));
        animalElement.appendChild(legsElement);
        
        Element weightElement = document.createElement("weight");
        weightElement.setTextContent(animal.getWeight().toString());
        animalElement.appendChild(weightElement);
        
        Element dateAddedElement = document.createElement("dateAdded");
        dateAddedElement.setTextContent(animal.getDateAdded().toString());
        animalElement.appendChild(dateAddedElement);
        
        Element lastFeedingTimeElement = document.createElement("lastFeedingTime");
        lastFeedingTimeElement.setTextContent(animal.getLastFeedingTime().toString());
        animalElement.appendChild(lastFeedingTimeElement);
        
        animalFragment.appendChild(animalElement);
        return animalFragment;
    }
    
    public void createHistoryRecord(Animal animal) throws AnimalDataException {
        verifyHistory();
        lookupHistory.put(LocalDateTime.now(), animal);
        saveToFile();
    }
    private void verifyHistory() throws AnimalDataException {
        if(null == lookupHistory){
            readFromFile();
        }
    }
    
    public Map<LocalDateTime, Animal> getSearchHistory() throws AnimalDataException {
        verifyHistory();
        return lookupHistory;
    }
}
