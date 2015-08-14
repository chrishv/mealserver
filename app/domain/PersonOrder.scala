package domain

import org.joda.time.LocalDate

case class PersonOrder(id: Option[Int],
                       order_date: LocalDate,
                       owning_person: Person,
                       vendor: Vendor,
											 status: String) 
  


object PersonOrder {
  
  def formApply(order_date: LocalDate, owning_person: Person, vendor: Vendor, status: String) =
    new PersonOrder(None, order_date, owning_person, vendor, status)

  def formUnapply(personOrder: PersonOrder): Option[(LocalDate, String, String)] =
    Some((personOrder.order_date, personOrder.owning_person.id.toString, personOrder.vendor.id.toString))
}