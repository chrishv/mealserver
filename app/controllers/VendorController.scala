package controllers

import domain.Vendor
import dal.VendorDal

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._

class VendorController extends Controller {

  val vendorDal = new VendorDal {}

  val vendorForm: Form[Vendor] = 
    Form(mapping(
           "name" -> nonEmptyText,
           "contact_tel_number" -> nonEmptyText,
           "contact_email" -> nonEmptyText
         )(Vendor.formApply)(Vendor.formUnapply))

  def showVendors = Action {
    val searchTerm = ""
    val vendorList = vendorDal.getVendors(searchTerm)
    Ok(views.html.main("MealServer - vendors", "vendorMenuItem")(views.html.vendors(vendorForm, "", vendorList)))
 
  }

  def searchVendors(searchTerm: String) = Action {
    val vendorList = vendorDal.getVendors(searchTerm)
    Ok(views.html.main("MealServer - vendors", "vendorMenuItem")(views.html.vendors(vendorForm, searchTerm, vendorList)))
  }
  
  def submitAddVendorForm = Action { implicit request =>
    vendorForm.bindFromRequest().fold(
    
      // Failure function:
      (formContainingErrors: Form[Vendor]) => {
        // Show the user a completed form with error messages:

        val searchTerm = ""
        val vendorList = vendorDal.getVendors(searchTerm)
        BadRequest(views.html.main("MealServer - vendors", "vendorMenuItem")(views.html.vendors(formContainingErrors, searchTerm, vendorList)))

      },

      // Success function:
      (vendor: Vendor) => {
 
        val newId = vendorDal.createVendor(vendor)
        Redirect(routes.VendorController.showVendors)
      }
    )
  }  
  
  def deleteVendor(vendorId: Int) = Action {

    vendorDal.deleteVendor(vendorId)
    Redirect(routes.VendorController.showVendors)

  }

}