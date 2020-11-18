/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Ryan
 * Created: Nov 18, 2020
 */

DROP DATABASE IF EXISTS animal_database;
CREATE DATABASE animal_database;
USE animal_database;

/* *****************************************************************************
	Create statement for table animal
***************************************************************************** */
DROP TABLE IF EXISTS animal;
CREATE TABLE animal(
    animal_id VARCHAR(300) NOT NULL,
    animal_name VARCHAR(300) NOT NULL,
    animal_species VARCHAR(25) NOT NULL,
    animal_gender VARCHAR(6) NOT NULL,
    animal_age INT NOT NULL,
    animal_fixed BOOLEAN NOT NULL,
    animal_legs INT NOT NULL,
    animal_weight DECIMAL(5,2) NOT NULL,
    dateAdded DATE NOT NULL,
    lastFeedingTime TIMESTAMP NOT NULL,
    PRIMARY KEY(animal_id)) 
;

/* *****************************************************************************
	Build Stored Procedure sp_get_an_animal
***************************************************************************** */
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_get_an_animal$$
CREATE PROCEDURE sp_get_an_animal( 
	IN p_original_animal_name VARCHAR(300)
) 
BEGIN
	SELECT
        animal_id,
        animal_name,
        animal_species,
        animal_gender,
        animal_age,
        animal_fixed,
        animal_legs,
        animal_weight,
        dateAdded,
        lastFeedingTime
	FROM animal
    WHERE animal_name = p_original_animal_name;
END$$
DELIMITER ;

/* *****************************************************************************
	Build Stored Procedure sp_add_animal
***************************************************************************** */
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_add_animal$$
CREATE PROCEDURE sp_add_animal(
    IN p_animal_id VARCHAR(300),
    IN p_animal_name VARCHAR(300),
    IN p_animal_species VARCHAR(25),
    IN p_animal_gender VARCHAR(6),
    IN p_animal_age INT,
    IN p_animal_fixed BOOLEAN,
    IN p_animal_legs INT,
    IN p_animal_weight DECIMAL(5,2),
    IN p_dateAdded DATE,
    IN p_lastFeedingTime TIMESTAMP
)
BEGIN
    INSERT INTO animal(
        animal_id,
        animal_name,
        animal_species,
        animal_gender,
        animal_age,
        animal_fixed,
        animal_legs,
        animal_weight,
        dateAdded,
        lastFeedingTime
    )
    VALUES (
        p_animal_id,
        p_animal_name,
        p_animal_species,
        p_animal_gender,
        p_animal_age,
        p_animal_fixed,
        p_animal_legs,
        p_animal_weight,
        p_dateAdded,
        p_lastFeedingTime
    );
END$$
DELIMITER ;
 

/* *****************************************************************************
	Add some data
***************************************************************************** */
CALL sp_add_animal('test1','Scruff Mc Gruff', 'dog', 'Male'
, 33, 1, 4, 170.25, '2010-06-22', '2020-11-18 12:30:15');
CALL sp_add_animal('test2','Liz', 'Lizzard', 'Female'
, 22, 0, 4, 015.50, '2020-11-10', '2020-11-18 12:30:15');
CALL sp_add_animal('test3','Scales', 'snake', 'Male'
, 40, 0, 0, 300.20, '2019-12-29', '2020-11-18 12:30:15');
CALL sp_add_animal('test4','Muffin', 'cat', 'Female'
, 15, 1, 4, 040.70, '2020-11-17', '2020-11-18 12:30:15');
CALL sp_add_animal('test5','Cookie Megatron Bunweenie The Third The Earl Of Yankovic The Bunny Formally Known As Cookie Monster And Sometimes Referred To As Captain Cook'
, 'Rabbit ', 'Female', 30, 1, 4, 050.40, '2020-11-18'
, '2020-11-18 12:30:15');

Call sp_get_an_animal('Cookie Megatron Bunweenie The Third The Earl Of Yankovic The Bunny Formally Known As Cookie Monster And Sometimes Referred To As Captain Cook');

/* *****************************************************************************
                               END OF SCRIPT	
***************************************************************************** */
