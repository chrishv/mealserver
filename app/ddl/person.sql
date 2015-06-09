CREATE TABLE person
  (
     PRIMARY KEY(person_id),
     
     person_id     INT NOT NULL,
     person_name   CHAR(100) NOT NULL,
     email_address CHAR(254) NOT NULL,
     
     CONSTRAINT mealserver_0001 CHECK(person_id > 0),
     CONSTRAINT mealserver_0002 CHECK(person_name <> ''),
     CONSTRAINT mealserver_0003 CHECK(email_address <> '')
  ); 