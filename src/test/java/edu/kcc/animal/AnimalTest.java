package edu.kcc.animal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author k0519415
 */
public class AnimalTest {
    //LocalDateTime.of(2020, 10, 1, 23, 59) LocalDate.of(2020, 9, 1)
    private Animal animal;
    private static final String GOOD_NAME = "Snowball";
    private static final String GOOD_ID = "0";
    private static final String GOOD_SPECIES = "cat";
    private static final String GOOD_GENDER = "Female";
    private static final int GOOD_AGE = 0;
    private static final boolean GOOD_FIXED = false;
    private static final int GOOD_LEGS = 4;
    private static final BigDecimal GOOD_WEIGHT = BigDecimal.valueOf(0);
    private static final LocalDate GOOD_DATE_ADDED = LocalDate.now();
    private static final LocalDateTime GOOD_LAST_FEEDING_TIME = LocalDateTime.now().withNano(0).withSecond(0);
    
    public AnimalTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        animal = new Animal();
    }
    
    @After
    public void tearDown() {
    }

    // TODO
    @Test
    public void testGetId() {
        setUp();
        String expected = "0";
        String actual = animal.getId();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetName() {
        setUp();
        String expected = "Unknown";
        String actual = animal.getName();
        assertEquals(expected, actual);
    }
    
    // TODO
    @Test
    public void testGetSpecies() {
        setUp();
        String expected = "cat";
        String actual = animal.getSpecies();
        assertEquals(expected, actual);
    }
    
    // TODO
    @Test
    public void testGetGender(){
        setUp();
        String expected = "Unknown";
        String result = animal.getGender();
        assertEquals(expected, result);
    }
    
    @Test
    public void testGetAge(){
        setUp();
        int expected=0;
        int result=animal.getAge();
        assertEquals(expected,result);
    }

    @Test
    public void testGetDateAdded() {
        setUp();
        LocalDate expResult = LocalDate.now(); // The default date
        LocalDate result = animal.getDateAdded();
        assertEquals(expResult.toString(), result.toString());
    }
    
    @Test
    public void testGetWeight() {
        setUp();
        BigDecimal expResult = new BigDecimal(0);
        BigDecimal result = animal.getWeight();
        assertEquals(expResult,result);
    }

    @Test
    public void testGetLegs(){
        setUp();
        int expected = 4;
        int actual = animal.getLegs();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetFixed(){
        setUp();
        boolean expected = false;
        boolean actual = animal.getFixed();
        assertEquals(expected, actual);
    }

    // TODO
    @Test
    public void testGetLastFeedingTime() {
        setUp();
        LocalDateTime expected = LocalDateTime.now().withNano(0).withSecond(0);
        LocalDateTime actual = animal.getLastFeedingTime();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSetName() {
        setUp();
        animal.setName(GOOD_NAME);
        assertEquals(GOOD_NAME, animal.getName());
    }
    
    // TODO
    @Test
    public void testSetSpeciesCat() {
        setUp();
        animal.setSpecies("cat");
        assertEquals("cat", animal.getSpecies());
    }
    
    // TODO
    @Test
    public void testSetSpeciesDog() {
        setUp();
        animal.setSpecies("dog");
        assertEquals("dog", animal.getSpecies());
    }
    
    // TODO - Attempt to set a non Cat or Dog
    @Test
    public void testSetSpeciesBad() {
        setUp();
        try{
            animal.setSpecies("dolphin");
            fail("Species cannot be set to dolphin");
        } catch(IllegalArgumentException ex) {
            assertTrue(true);
        }
    }
    
    // TODO - Attempt to set a cat to a dog
    @Test
    public void testSetSpeciesBadCatToDog() {
        setUp();
        try{
            animal.setSpecies("cat");
            animal.setSpecies("dog");
        } catch(IllegalArgumentException ex) {
            assertTrue(true);
        }
    }
    // TODO
    @Test
    public void testSetGender() {
        String gender = "female";
        animal.setGender(gender);
        assertEquals(gender, animal.getGender());
    }
    
    // TODO - Attempt to set non male or female
    @Test
    public void testSetGenderBad() {
        setUp();
        String gender = "vegetable";
        String original = animal.getGender();
        try{
            animal.setGender(gender);
        }
        catch (IllegalArgumentException iae){
            assertEquals(original, animal.getGender());
        }
    }
    
     // TODO - Attempt to set a male to female
    @Test
    public void testSetGenderBadMaleToFemale() {
        setUp();
        animal.setGender("male");
        String original = animal.getGender();
                
        try{
            animal.setGender("female");
        }
        catch (IllegalArgumentException iae){
            assertEquals(original, animal.getGender());
        }
    }
    
    // TODO
    @Test
    public void testSetAge() {
        animal.setAge(3);
        assertEquals(3,animal.getAge());
    }
    
    // TODO
    @Test
    public void testSetLegs() {
        animal.setLegs(3);
        assertEquals(3,animal.getLegs());
    }
    
    // TODO
    @Test
    public void testSetDateAdded() {
        LocalDate goodDate = LocalDate.now();
        animal.setDateAdded(goodDate);
        assertEquals(goodDate, animal.getDateAdded());
    }
    
    @Test
    public void testSetDateAddedMoreThanAWeekAgoBad() {
        LocalDate badDate = LocalDate.now().minusWeeks(2);
        LocalDate original = animal.getDateAdded();
        try{
            animal.setDateAdded(badDate);           
        }
        catch(IllegalArgumentException iae){
            assertEquals(original, animal.getDateAdded());
        }
    }
    
    @Test
    public void testSetDateAddedFutureDateBad() {
        LocalDate badDate = LocalDate.now().plusDays(1);
        LocalDate original = animal.getDateAdded();
        try{
            animal.setDateAdded(badDate);           
        }
        catch(IllegalArgumentException iae){
            assertEquals(original, animal.getDateAdded());
        }
    }
    
    // TODO
    @Test
    public void testSetAgeNegativeBad() {
        int negAge = -3;
        try{
            animal.setAge(negAge);           
        }
        catch(IllegalArgumentException iae){
            assertEquals(-3, animal.getAge());
        }
    }
    
    @Test
    public void testSetLastFeedingTimeMoreThanTwoDaysAgoBad() {
        LocalDateTime ldtThreeDays = LocalDateTime.now().minusDays(3);
        try{
            animal.setLastFeedingTime(ldtThreeDays);
        }
        catch(IllegalArgumentException iae){
            assertEquals(3+3, 6);
        }
    }
    
    @Test
    public void testSetLastFeedingTimeFutureDateBad() {
        LocalDateTime ldtFutureDate = LocalDateTime.now().plusDays(1);
        try{
            animal.setLastFeedingTime(ldtFutureDate);
        }
        catch(IllegalArgumentException iae){
            assertEquals(3+3, 6);
        }
        
    }

    // TODO - Set the ID to something not already set
    @Test
    public void testSetId() {
        animal.setId("test1");
        assertEquals("test1", animal.getId());
    }
    
    // TODO - Create a second Animal object with a unique id. Try to set the first animal's id equal to the second one
    @Test
    public void testSetIdBad() {
        
        try{
            animal.setId("test1");
            animal.setId("test1");
        }
        catch(IllegalArgumentException iae){
            assertEquals(3+3, 6);
        }
    }
    
    // TODO
    @Test
    public void testSetAgeOver100Bad() {
        try{
            animal.setAge(1011);
        }
        catch(IllegalArgumentException iae){
            assertEquals(3+3, 6);
        }
    }

    // TODO
    @Test
    public void testSetFixed() {
        animal.setFixed(true);
        assertEquals(true, animal.getFixed());
    }

    // TODO - Attempt to set a fixed animal to not fixed
    @Test
    public void testSetFixedTruetoFalse() {
        try{
            animal.setFixed(true);
            animal.setFixed(false);
        }
        catch(IllegalArgumentException iae){
            assertEquals(3+3, 6);
        }
    }

    // TODO
    @Test
    public void testSetLegsNegativeBad() {
        try{
            animal.setLegs(-33);
        }
        catch(IllegalArgumentException iae){
            assertEquals(3+3, 6);
        }
    }
    
    // TODO
    @Test
    public void testSetLegsGreaterThan4Bad() {
        try{
            animal.setLegs(33);
        }
        catch(IllegalArgumentException iae){
            assertEquals(3+3, 6);
        }
    }
    
    // TODO
    @Test
    public void testSetWeight() {
        animal.setWeight(GOOD_WEIGHT);
        assertEquals(GOOD_WEIGHT, animal.getWeight());
    }
    
    // TODO
    @Test
    public void testSetWeightNegativeBad() {
        try{
            animal.setWeight(BigDecimal.valueOf(-330));
        }
        catch(IllegalArgumentException iae){
            assertEquals(3+3, 6);
        }
    }
    
    // TODO
    @Test
    public void testSetWeightAbove1000Bad() {
        try{
            animal.setWeight(BigDecimal.valueOf(333333330));
        }
        catch(IllegalArgumentException iae){
            assertEquals(3+3, 6);
        }
    }

    // TODO
    @Test
    public void testSetLastFeedingTime() {
        animal.setLastFeedingTime(GOOD_LAST_FEEDING_TIME);
        assertEquals(GOOD_LAST_FEEDING_TIME, animal.getLastFeedingTime());
    }
    
    // TODO
    @Test
    public void testToString() {
        setUp();
        String expResult = animal+"";
        String result = animal.toString();
        assertEquals(expResult, result);
    }

    private static final String OTHER_ID = "1";
    @Test
    public void testCompareToZero() {
        int expResult = 0;
        int result = GOOD_ID.compareTo(GOOD_ID);
        assertTrue(result == expResult);
    }
    
    @Test
    public void testCompareToPositive() {
        int result = OTHER_ID.compareTo(GOOD_ID);
        assertTrue(result > 0);
    }
    
    @Test
    public void testCompareToNegative() {
        int result = GOOD_ID.compareTo(OTHER_ID);
        assertTrue(result < 0);
    }
    
}
