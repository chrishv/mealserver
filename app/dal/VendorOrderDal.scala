package dal

import sqlest._
import domain.VendorOrder
import org.joda.time.LocalDate


class VendorOrderTable(alias: Option[String]) extends Table("vendor_order", alias) {
  val id = column[Int]("vendor_order_id")
  val vendor_id = column[Int]("vendor_id")
  val order_date = column[LocalDate]("order_date")
  val owning_person = column[Int]("owning_person")
  val status = column[String]("status")
}

object VendorOrderTable extends VendorOrderTable(None)

trait VendorOrderDal extends SqlestDb {

  def getVendorOrders(searchTerm: String) : List[VendorOrder] = {
    if (searchTerm != "") {
    val wildCardSearch = "%" + searchTerm + "%"
    val upper = ScalarFunction1[String, String]("upper")
    select
  	  .from(VendorOrderTable)
      .innerJoin(VendorTable).on(VendorOrderTable.vendor_id === VendorTable.id)
      .innerJoin(PersonTable).on(VendorOrderTable.owning_person === PersonTable.id)
      .where(upper(VendorTable.name) like wildCardSearch.toUpperCase)
      .extractAll(vendorOrderExtractor)}
    else
      select
      .from(VendorOrderTable)
      .innerJoin(VendorTable).on(VendorOrderTable.vendor_id === VendorTable.id)
      .innerJoin(PersonTable).on(VendorOrderTable.owning_person === PersonTable.id)
      .extractAll(vendorOrderExtractor)
  }

  def createVendorOrder(vendorOrder: VendorOrder): Int = {

  	if (canCreateVendorOrder(vendorOrder))

      database.withTransaction {

        val insertStatement = 
          insert
            .into(VendorOrderTable)
            .values(VendorOrderTable.vendor_id -> vendorOrder.vendor.id.getOrElse(0),
                    VendorOrderTable.order_date -> vendorOrder.order_date,
                    VendorOrderTable.owning_person -> vendorOrder.owning_person.id.getOrElse(0),
                    VendorOrderTable.status -> vendorOrder.status).execute
  
        val newId = select(max(VendorOrderTable.id))
                      .from(VendorOrderTable)
                      .fetchHead

         newId match {
          case Some(i) => i
         	case _ => throw new DataException("Database error")
         }

      }     

    else throw new Exception("Invalid vendor order")

  }

  def canCreateVendorOrder(vendorOrder: VendorOrder): Boolean = {
    // Just got to check it's not already got an id
    !vendorOrder.id.isDefined
  }

  def canUpdateVendorOrder(vendorOrder: VendorOrder): Boolean = {
    vendorOrder.id.isDefined
  }

  def deleteVendorOrder(vendorOrderId: Int) = {
    database.withTransaction {
      delete
        .from(VendorOrderTable)
        .where(VendorOrderTable.id === vendorOrderId).execute
    }      
  }      

  def updateVendorOrder(vendorOrder: VendorOrder) = {
    if (canUpdateVendorOrder(vendorOrder)) {
      val updateStatement =
        update(VendorOrderTable)
          .set(
            vendorOrderExtractor.settersFor(vendorOrder)
              .filter(_.column.tableAlias == VendorOrderTable.tableAlias))
          .where(VendorOrderTable.id === vendorOrder.id.getOrElse(0))

      database.withTransaction {
        updateStatement.execute
      }
    }   
    else throw new DataException("Can't update this vendor order")
  }

  lazy val vendorOrderExtractor = extract[VendorOrder](
    id = VendorOrderTable.id.asOption,
    vendor = new VendorDal{}.vendorExtractor,
    order_date = VendorOrderTable.order_date,
    owning_person = new PersonDal{}.personExtractor,
    status = VendorOrderTable.status

  )

}

