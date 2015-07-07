package domain

trait TakesOrder {
//  override def orderSandwiches (order:List[(Person,Sandwich)],totalSoFar:Double):Double = {
//    println(totalSoFar)
//    if (order.isEmpty) totalSoFar 
//    else if (!placeOrder(order.head._2)) throw new Error(order.head._2.sandwichName + " not on menu!")
//    else orderSandwiches(order.tail,totalSoFar+order.head._2.price)
//  }
  
def placeOrder(sandwich:Sandwich):Boolean
}