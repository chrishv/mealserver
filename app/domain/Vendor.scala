package domain

/**
 * @author Matthew
 */
case class Vendor (val id: Option[Int], val name: String, val contact_tel_number: String, contact_email: String) {

    def orderSandwiches (order:List[(Person,Sandwich)],totalSoFar:Double):Double = {
    println(totalSoFar)
    if (order.isEmpty) totalSoFar 
    else if (!placeOrder(order.head._2)) throw new Error(order.head._2.sandwichName + " not on menu!")
    else orderSandwiches(order.tail,totalSoFar+order.head._2.price)
  }
  
  def placeOrder (sandwich:Sandwich):Boolean = ???
}

object Vendor {

  def formApply(name: String, contact_tel_number: String, contact_email:String) =
    new Vendor(None, name, contact_tel_number, contact_email)

  def formUnapply(vendor: Vendor): Option[(String,String,String)] = 
    Some((vendor.name,vendor.contact_tel_number,vendor.contact_email)) 

}

object Tuckers extends Vendor(Some(1),"tuckers","0121 123456","getf@ter.com") with TakesOrder{
  
  val sausage = new Sandwich("Sausage","white","sausage",1.23)
  val bacon = new Sandwich("Bacon","white","bacon",1.45)
  val menu=List[Sandwich](sausage,bacon)
  
  override def placeOrder(sandwich:Sandwich):Boolean = {
    menu.contains(sandwich)
  }
}