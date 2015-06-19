package domain

import org.joda.time.LocalDate

case class Order(val id: Int, 
	             val orderType: String, 
	             val person: Person, 
	             val vendor: Vendor,
                 val item : Food,
	             val date: LocalDate,
	             val note: Option[String])

