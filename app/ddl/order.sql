CREATE TABLE order
(
   PRIMARY KEY(order_id),
   
   order_id     INT NOT NULL GENERATED ALWAYS AS IDENTITY,,
   order_type   CHAR(100) NOT NULL,
   person_id    INT NOT NULL,
   vendor_id    INT NOT NULL,
   order_date   DATE
   
   CONSTRAINT order_0001  CHECK(order_type <> '')
   
   --Foreign Key Constraints   
   CONSTRAINT order_FK_person_0001 
     FOREIGN KEY (person_id)       
     REFERENCES person(person_id)  
     ON DELETE RESTRICT                 
     ON UPDATE RESTRICT
     
   CONSTRAINT order_FK_vendor_0001 
     FOREIGN KEY (vendor_id)       
     REFERENCES vendor(vendor_id)  
     ON DELETE RESTRICT                 
     ON UPDATE RESTRICT
); 