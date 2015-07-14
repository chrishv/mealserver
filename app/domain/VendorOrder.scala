package domain

import org.joda.time.LocalDate

case class VendorOrder(id: Option[Int],
                       vendor: Vendor,
                       order_date: LocalDate,
                       owning_person: Person,
											 //val orders: Option[List[Order]],
											 status: String)

object VendorOrder {

  def formApply(vendor: Vendor, order_date: LocalDate, owning_person: Person, status: String) =
    new VendorOrder(None, vendor, order_date, owning_person, status)

  def formUnapply(vendorOrder: VendorOrder): Option[(String, LocalDate, String)] =
    Some((vendorOrder.vendor.id.toString, vendorOrder.order_date, vendorOrder.owning_person.id.toString))

}
