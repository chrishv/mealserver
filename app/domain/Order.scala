package uk.co.benmulhern.mealserver.domain

import org.joda.time.LocalDate

case class Order(val id: Int, 
	             val orderType: String, 
	             val personId: Int, 
	             val vendorId: Int, 
	             val date: LocalDate,
	             val note: Option[String])

