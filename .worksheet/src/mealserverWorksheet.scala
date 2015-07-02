object mealserverWorksheet {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(72); 
  println("Welcome to the Scala worksheet")
  
  import sqlest._
  
  class VendorTable(alias: Option[String]) extends Table("vendor", alias) {
  val id = column[Int]("vendor_id")
  val name = column[String]("name")
  };$skip(213); 
  
  val testString = Some("dfgfdg");System.out.println("""testString  : Some[String] = """ + $show(testString ));$skip(40); 
  val name: Option[String] = testString;System.out.println("""name  : Option[String] = """ + $show(name ))}
  
  object VendorTable extends VendorTable (None){}
  // val x = new TableColumn[Int](1,"id")
}
