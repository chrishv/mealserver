package dal

import sqlest._
import domain.PersonOrder
import org.joda.time.LocalDate


class PersonOrderTable(alias: Option[String]) extends Table("person_order", alias) {
  val id = column[Int]("person_order_id")
  val vendor_id = column[Int]("vendor_id")
  val order_date = column[LocalDate]("order_date")
  val owning_person = column[Int]("owning_person")
  val status = column[String]("status")
}

object PersonOrderTable extends PersonOrderTable(None)

trait PersonOrderDal extends SqlestDb {

  def getPersonOrders(searchTerm: String) : List[PersonOrder] = {
    if (searchTerm != "") {
    val wildCardSearch = "%" + searchTerm + "%"
    val upper = ScalarFunction1[String, String]("upper")
    select
  	  .from(PersonOrderTable)
      .innerJoin(VendorTable).on(PersonOrderTable.vendor_id === VendorTable.id)
      .innerJoin(PersonTable).on(PersonOrderTable.owning_person === PersonTable.id)
      .where(upper(VendorTable.name) like wildCardSearch.toUpperCase)
      .extractAll(personOrderExtractor)}
    else
      select
      .from(PersonOrderTable)
      .innerJoin(VendorTable).on(PersonOrderTable.vendor_id === VendorTable.id)
      .innerJoin(PersonTable).on(PersonOrderTable.owning_person === PersonTable.id)
      .extractAll(personOrderExtractor)
  }

  def createPersonOrder(personOrder: PersonOrder): Int = {

  	if (canCreatePersonOrder(personOrder))

      database.withTransaction {

        val insertStatement = 
          insert
            .into(PersonOrderTable)
            .values(PersonOrderTable.vendor_id -> personOrder.vendor.id.getOrElse(0),
                    PersonOrderTable.order_date -> personOrder.order_date,
                    PersonOrderTable.owning_person -> personOrder.owning_person.id.getOrElse(0),
                    PersonOrderTable.status -> personOrder.status).execute
  
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

  def canCreatePersonOrder(personOrder: PersonOrder): Boolean = {
    // Just got to check it's not already got an id
    !personOrder.id.isDefined
  }

  def canUpdatePersonOrder(personOrder: PersonOrder): Boolean = {
    personOrder.id.isDefined
  }

  def deletePersonOrder(personOrderId: Int) = {
    database.withTransaction {
      delete
        .from(PersonOrderTable)
        .where(PersonOrderTable.id === personOrderId).execute
    }      
  }      

  def updatePersonOrder(personOrder: PersonOrder) = {
    if (canUpdatePersonOrder(personOrder)) {
      val updateStatement =
        update(PersonOrderTable)
          .set(
            personOrderExtractor.settersFor(personOrder)
              .filter(_.column.tableAlias == VendorOrderTable.tableAlias))
          .where(VendorOrderTable.id === personOrder.id.getOrElse(0))

      database.withTransaction {
        updateStatement.execute
      }
    }   
    else throw new DataException("Can't update this person order")
  }

  lazy val personOrderExtractor = extract[PersonOrder](
    id = PersonOrderTable.id.asOption,
    vendor = new VendorDal{}.vendorExtractor,
    order_date = PersonOrderTable.order_date,
    owning_person = new PersonDal{}.personExtractor,
    status = PersonOrderTable.status

  )

}

