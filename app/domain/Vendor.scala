package uk.co.benmulhern.mealserver.domain

/**
 * @author Matthew
 */
abstract class Vendor {

  def orderSandwiches (order:List[(Person,Sandwich)],totalSoFar:Double):Double
  
  def placeOrder (sandwich:Sandwich):Boolean
}


object Tuckers extends Vendor {
  
  val sausage = new Sandwich("Sausage","white","sausage",1.23)
  val bacon = new Sandwich("Bacon","white","bacon",1.45)
  val menu=List[Sandwich](sausage,bacon)
  
  def orderSandwiches (order:List[(Person,Sandwich)],totalSoFar:Double):Double = {
    if (order.isEmpty) totalSoFar 
    else if (!placeOrder(order.head._2)) throw new Error(order.head._2.sandwichName + " not on menu!")
    else orderSandwiches(order.tail,totalSoFar+order.head._2.price)
  }
  
  def placeOrder(sandwich:Sandwich):Boolean = {
    menu.contains(sandwich)
  }
}