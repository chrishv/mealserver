CREATE TABLE ORDER_ITEM 
  ( 
     PRIMARY KEY(order_id), 
     
          order_id   INT NOT NULL, 
          vendor_id  INT NOT NULL, 
          item_id    INT NOT NULL,
          item_name  CHAR(200) NOT NULL,
          cost       DEC NOT NULL,
          
     CONSTRAINT order_item_0001 CHECK(order_type <> ''), 
     --Foreign Key Constraints    
     CONSTRAINT order_item_fk_person_0001 FOREIGN KEY (person_id) REFERENCES person( 
     person_id) ON DELETE RESTRICT ON UPDATE RESTRICT, 
     CONSTRAINT order_item_fk_vendor_0001 FOREIGN KEY (vendor_id) REFERENCES vendor( 
     vendor_id) ON DELETE RESTRICT ON UPDATE RESTRICT 
  ); 