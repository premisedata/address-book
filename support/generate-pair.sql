USE address_book;
INSERT INTO person_address
  SELECT
    (SELECT p.id
     FROM person p
     WHERE NOT EXISTS (SELECT * FROM person_address pa WHERE pa.person_id = p.id)
     ORDER BY RAND()
     LIMIT 1) AS person_id,
    (SELECT a.id
     FROM address a
     WHERE NOT EXISTS (SELECT * FROM person_address pa WHERE pa.address_id = a.id)
     ORDER BY RAND()
     LIMIT 1) AS address_id;
