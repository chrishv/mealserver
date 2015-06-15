package uk.co.benmulhern.mealserver.domain

/**
 * @author Matthew
 */
abstract class Vendor {

  def orderSandwiches (order:List[(Person,Sandwich)],totalSoFar:Double):Double
  
  def placeOrder (sandwich:Sandwich):Boolean
}


object Tuckers extends Vendor with TakesOrder{
  
  val sausage = new Sandwich("Sausage","white","sausage",1.23)
  val bacon = new Sandwich("Bacon","white","bacon",1.45)
  val menu=List[Sandwich](sausage,bacon)
  
  def placeOrder(sandwich:Sandwich):Boolean = {
    menu.contains(sandwich)
  }
}