package uk.co.benmulhern.mealserver.domain

case class Person(val id: Int, val forename: String, 
                  val surname: String, val email: String, val usualItem:Sandwich)

