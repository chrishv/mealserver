package uk.co.benmulhern.mealserver.domain

/**
 * @author Matthew
 */
abstract class Food {
val edible = true
}

class Sandwich (val sandwichName:String, val breadType:String, val filling:String, val price:Double) extends Food  {

  
}