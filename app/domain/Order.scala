package uk.co.benmulhern.mealserver.domain

case class Order(val id: Int, val type: String, val person_id: Int, val vendor_id: Int, val date: Date)

