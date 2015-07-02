package dal

import sqlest._
import domain.Vendor


class VendorTable(alias: Option[String]) extends Table("vendor", alias) {
  val id = column[Int]("vendor_id")
  val name = column[String]("name")
  val contact_tel_number = column[String]("contact_tel_number")
  val contact_email = column[String]("contact_email")
}

object VendorTable extends VendorTable(None)

trait VendorDal extends SqlestDb {

  def getVendors(searchTerm: String) : List[Vendor] = {
    if (searchTerm != "") {
    val wildCardSearch = ("%" + searchTerm + "%")  
    select
  	  .from(VendorTable)
  	  .where(VendorTable.name like wildCardSearch)
      .extractAll(vendorExtractor)}
    else
      select
      .from(VendorTable)
      .extractAll(vendorExtractor)    
  }

  def createVendor(vendor: Vendor): Int = {

  	if (canCreateVendor(vendor))

      database.withTransaction {

        val insertStatement = 
          insert
            .into(VendorTable)
            .values(VendorTable.name -> vendor.name,
                    VendorTable.contact_tel_number -> vendor.contact_tel_number,
                    VendorTable.contact_email -> vendor.contact_email).execute
  
        val newId = select(max(VendorTable.id))
                      .from(VendorTable)
                      .fetchHead

         newId match {
          case Some(i) => i
         	case _ => throw new DataException("Database error")
         }

      }     

    else throw new Exception("Invalid vendor")

  }

  def canCreateVendor(vendor: Vendor): Boolean = {
    // Just got to check it's not already got an id
    !vendor.id.isDefined
  }

  def canUpdateVendor(vendor: Vendor): Boolean = {
    vendor.id.isDefined
  }

  def deleteVendor(vendorId: Int) = {
    database.withTransaction {
      delete
        .from(VendorTable)
        .where(VendorTable.id === vendorId).execute
    }      
  }      

  def updateVendor(vendor: Vendor) = {
    if (canUpdateVendor(vendor)) {
      val updateStatement =
        update(VendorTable)
          .set(
            vendorExtractor.settersFor(vendor)
              .filter(_.column.tableAlias == VendorTable.tableAlias))
          .where(VendorTable.id === vendor.id)

      database.withTransaction {
        updateStatement.execute
      }
    }   
    else throw new DataException("Can't update this vendor")  
  }

  lazy val vendorExtractor = extract[Vendor](
    id = VendorTable.id.asOption,
    name = VendorTable.name,
    contact_tel_number = VendorTable.contact_tel_number,
    contact_email = VendorTable.contact_email

  )

}

