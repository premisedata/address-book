USE address_book;

-- Create tables
CREATE TABLE person (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(255) NOT NULL,
  lastName VARCHAR(255) NOT NULL,
  gender ENUM('male', 'female') NOT NULL,
  dateOfBirth DATE NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE address (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  street VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  state VARCHAR(50) NOT NULL,
  postalCode VARCHAR(10) NOT NULL,
  country VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE person_address (
  person_id INT UNSIGNED NOT NULL,
  address_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (person_id, address_id),
  FOREIGN KEY (person_id) REFERENCES person(id),
  FOREIGN KEY (address_id) REFERENCES address(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- First, add people who share a last name
-- Actually, scrapping this for simplicity's sake...
/*
INSERT INTO person (firstName, lastName, gender, dateOfBirth)
SELECT firstName, lastName, 'male', dateOfBirth
FROM males m
WHERE EXISTS (SELECT * FROM females f WHERE f.lastName = m.lastName);
INSERT INTO person (firstName, lastName, gender, dateOfBirth)
SELECT firstName, lastName, 'female', dateOfBirth
FROM females f
WHERE EXISTS (SELECT * FROM males m WHERE m.lastName = f.lastName);
*/

-- Add some more random people
INSERT INTO person (firstName, lastName, gender, dateOfBirth)
  SELECT firstName, lastName, 'male', dateOfBirth
  FROM males m
  WHERE NOT EXISTS (SELECT * FROM females f WHERE f.lastName = m.lastName)
  ORDER BY RAND()
  LIMIT 25;
INSERT INTO person (firstName, lastName, gender, dateOfBirth)
  SELECT firstName, lastName, 'female', dateOfBirth
  FROM females f
  WHERE NOT EXISTS (SELECT * FROM males m WHERE m.lastName = f.lastName)
  ORDER BY RAND()
  LIMIT 25;

-- Add some random addresses
INSERT INTO address (street, city, state, postalCode, country)
  SELECT street, city, state, postalCode, country
  FROM mock_address_data
  ORDER BY RAND()
  LIMIT 50;

-- Generate random person/address pairs
-- TODO: I couldn't get the loop to work, so rather than wasting more time on
-- it, for the time being, I just manually ran the INSERT 50 times...
-- DROP PROCEDURE IF EXISTS generate_pa_pairs;
-- DELIMITER $$;
-- CREATE PROCEDURE generate_pa_pairs()
-- BEGIN
--   WHILE EXISTS (SELECT * FROM person WHERE person.id NOT IN (SELECT DISTINCT person_id FROM person_address)) DO
--     INSERT INTO person_address
--       SELECT
--         (SELECT p.id
--          FROM person p
--          WHERE NOT EXISTS (SELECT * FROM person_address pa WHERE pa.person_id = p.id)
--          ORDER BY RAND()
--          LIMIT 1) AS person_id,
--         (SELECT a.id
--          FROM address a
--          WHERE NOT EXISTS (SELECT * FROM person_address pa WHERE pa.address_id = a.id)
--          ORDER BY RAND()
--          LIMIT 1) AS address_id;
--   END WHILE;
-- END $$
-- DELIMITER ;$$