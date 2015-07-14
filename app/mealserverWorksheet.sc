object mealserverWorksheet {
  println("Welcome to the Scala worksheet")

  import sqlest._
  import dal.VendorDal

  class VendorTable(alias: Option[String]) extends Table("vendor", alias) {
    val id = column[Int]("vendor_id")
    val name = column[String]("name")
  }

  val testString = Some("dfgfdg")
  val name: Option[String] = testString

  object VendorTable extends VendorTable(None) {}

  // val x = new TableColumn[Int](1,"id")

  val searchTerm = ""
  val vendorDal = new VendorDal {}

  val vendorList = vendorDal.getVendors(searchTerm)
}