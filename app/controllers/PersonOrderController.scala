

package controllers


import domain.PersonOrder
import dal.PersonOrderDal
import dal.VendorDal
import dal.PersonDal

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._

class PersonOrderController extends Controller {

  val personOrderDal = new PersonOrderDal {}
  val vendorDal = new VendorDal {}
  val personDal = new PersonDal {}

  val personOrderForm: Form[PersonOrder] =
    Form(mapping(
           "order_date" -> jodaLocalDate("dd-MM-yyyy"),
           "vendor_id" -> nonEmptyText,
           "owning_person" -> nonEmptyText
         )(( order_date, vendor_id, owning_person) => PersonOrder.formApply(order_date,
                                                                           personDal.getPerson(owning_person.toInt),
                                                                           vendorDal.getVendor(vendor_id.toInt),
                                                                            "NEW"
                                                   ))(PersonOrder.formUnapply))

  def showPersonOrders = Action {
    val searchTerm = ""
    val personOrderList = personOrderDal.getPersonOrders(searchTerm)
    val vendorList = vendorDal.getVendors(searchTerm)
    val personList = personDal.getPersons(searchTerm)
    Ok(views.html.main("MealServer - orders", "personOrderMenuItem")(views.html.personOrders(personOrderForm, "", personOrderList, vendorList, personList)))

  } 

  def searchPersonOrders(searchTerm: String) = Action {
    val personOrderList = personOrderDal.getPersonOrders(searchTerm)
    val vendorList = vendorDal.getVendors("")
    val personList = personDal.getPersons("")
    Ok(views.html.main("MealServer - orders", "personOrderMenuItem")(views.html.personOrders(personOrderForm, searchTerm, personOrderList, vendorList, personList)))
  }
  
  def submitAddPersonOrderForm = Action { implicit request =>
    personOrderForm.bindFromRequest().fold(
    
      // Failure function:
      (formContainingErrors: Form[PersonOrder]) => {
        // Show the user a completed form with error messages:

        val searchTerm = ""
        val personOrderList = personOrderDal.getPersonOrders(searchTerm)
        val vendorList = vendorDal.getVendors(searchTerm)
        val personList = personDal.getPersons(searchTerm)
        BadRequest(views.html.main("MealServer - orders", "personOrderMenuItem")(views.html.personOrders(formContainingErrors, searchTerm, personOrderList, vendorList, personList)))

      },

      // Success function:
      (personOrder: PersonOrder) => {

        val newId = personOrderDal.createPersonOrder(personOrder)
        Redirect(routes.PersonOrderController.showPersonOrders())
      }
    )
  }  
  
  def deletePersonOrder(personOrderId: Int) = Action {

    personOrderDal.deletePersonOrder(personOrderId)
    Redirect(routes.PersonOrderController.showPersonOrders())

  }

}