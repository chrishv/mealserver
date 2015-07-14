package dal

object CreateNewDb extends SqlestDb {

  def generateNewDb() = {

    executeRawSql("""CREATE TABLE person
                       (
                          PRIMARY KEY(person_id),
      
                          person_id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
                          forename CHAR(50) NOT NULL,
	                      surname CHAR(50) NOT NULL,
                          email_address CHAR(254) NOT NULL,
     
                          CONSTRAINT person_0001 CHECK(person_id > 0),
                          CONSTRAINT person_0002 CHECK(forename <> ''),
	                      CONSTRAINT person_0003 CHECK(surname <> ''),
                          CONSTRAINT person_0004 CHECK(email_address <> '')
                       )"""
                )

    executeRawSql("""CREATE TABLE vendor
                       (
                          PRIMARY KEY(vendor_id),
      
                          vendor_id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
                          name CHAR(50) NOT NULL,
                          contact_tel_number CHAR(50) NOT NULL,
                          contact_email CHAR(50) NOT NULL,
     
                          CONSTRAINT vendor_0001 CHECK(vendor_id > 0),
                          CONSTRAINT vendor_0002 CHECK(name <> ''),
                          CONSTRAINT vendor_0003 CHECK((contact_tel_number <> '') OR (contact_email <> ''))
                       )"""
                )

    executeRawSql("""CREATE TABLE vendor_order
                       (
                          PRIMARY KEY(vendor_order_id),

                          vendor_order_id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
                          vendor_id INT NOT NULL,
						  order_date DATE NOT NULL,
						  owning_person INT NOT NULL,
                          status CHAR(12) NOT NULL,
						  
						  CONSTRAINT vendor_order_0001 CHECK(vendor_order_id > 0),
						  CONSTRAINT vendor_order_fk_vendor_0001 FOREIGN KEY (vendor_id) REFERENCES vendor( 
							vendor_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
						  CONSTRAINT vendor_order_fk_person_0001 FOREIGN KEY (owning_person) REFERENCES person( 
							person_id) ON DELETE RESTRICT ON UPDATE RESTRICT	
						  -- TODO - Check constaints on status	
						)"""
				)
  }

}