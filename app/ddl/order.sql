CREATE TABLE order
(
   PRIMARY KEY(order_id),
   
   order_id     INT NOT NULL GENERATED ALWAYS AS IDENTITY,,
   order_type   CHAR(100) NOT NULL,
   person_id    INT NOT NULL,
   vendor_id    INT NOT NULL,
   order_date   DATE
   
   CONSTRAINT order_0001  CHECK(order_type <> '')
); 