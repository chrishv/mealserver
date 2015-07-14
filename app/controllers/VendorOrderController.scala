package controllers

import domain.VendorOrder
import dal.VendorOrderDal
import dal.VendorDal
import dal.PersonDal

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._

class VendorOrderController extends Controller {

  val vendorOrderDal = new VendorOrderDal {}
  val vendorDal = new VendorDal {}
  val personDal = new PersonDal {}

  val vendorOrderForm: Form[VendorOrder] =
    Form(mapping(
           "vendor_id" -> nonEmptyText,
           "order_date" -> jodaLocalDate("dd-MM-yyyy"),
           "owning_person" -> nonEmptyText
         )((vendor_id, order_date, owning_person) => VendorOrder.formApply(vendorDal.getVendor(vendor_id.toInt),
                                                                           order_date,
                                                                           personDal.getPerson(owning_person.toInt),
                                                                           "NEW"
                                                   ))(VendorOrder.formUnapply))

  def showVendorOrders = Action {
    val searchTerm = ""
    val vendorOrderList = vendorOrderDal.getVendorOrders(searchTerm)
    val vendorList = vendorDal.getVendors(searchTerm)
    val personList = personDal.getPersons(searchTerm)
    Ok(views.html.main("MealServer - orders", "vendorOrderMenuItem")(views.html.vendorOrders(vendorOrderForm, "", vendorOrderList, vendorList, personList)))

  }

  def searchVendorOrders(searchTerm: String) = Action {
    val vendorOrderList = vendorOrderDal.getVendorOrders(searchTerm)
    val vendorList = vendorDal.getVendors("")
    val personList = personDal.getPersons("")
    Ok(views.html.main("MealServer - orders", "vendorOrderMenuItem")(views.html.vendorOrders(vendorOrderForm, searchTerm, vendorOrderList, vendorList, personList)))
  }
  
  def submitAddVendorOrderForm = Action { implicit request =>
    vendorOrderForm.bindFromRequest().fold(
    
      // Failure function:
      (formContainingErrors: Form[VendorOrder]) => {
        // Show the user a completed form with error messages:

        val searchTerm = ""
        val vendorOrderList = vendorOrderDal.getVendorOrders(searchTerm)
        val vendorList = vendorDal.getVendors(searchTerm)
        val personList = personDal.getPersons(searchTerm)
        BadRequest(views.html.main("MealServer - orders", "vendorOrderMenuItem")(views.html.vendorOrders(formContainingErrors, searchTerm, vendorOrderList, vendorList, personList)))

      },

      // Success function:
      (vendorOrder: VendorOrder) => {

        val newId = vendorOrderDal.createVendorOrder(vendorOrder)
        Redirect(routes.VendorOrderController.showVendorOrders())
      }
    )
  }  
  
  def deleteVendorOrder(vendorOrderId: Int) = Action {

    vendorOrderDal.deleteVendorOrder(vendorOrderId)
    Redirect(routes.VendorOrderController.showVendorOrders())

  }

}