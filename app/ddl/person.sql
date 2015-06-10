CREATE TABLE person
  (
     PRIMARY KEY(person_id),
     
     person_id     INT NOT NULL,
     person_name   CHAR(100) NOT NULL,
     email_address CHAR(254) NOT NULL,
     
     CONSTRAINT person_0001 CHECK(person_id > 0),
     CONSTRAINT person_0002 CHECK(person_name <> ''),
     CONSTRAINT person_0003 CHECK(email_address <> '')
  ); 