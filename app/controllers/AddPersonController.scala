package controllers

import domain.Person
import dal.PersonDal

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._

class AddPersonController extends Controller {

  val dal = PersonDal

  val personForm: Form[Person] = 
    Form(mapping(
           "forename" -> text,
           "surname"  -> text,
           "email"    -> text
         )(Person.formApply)(Person.formUnapply))

  def showForm = Action {
    val personList = PersonDal.getPersons()
    Ok(views.html.main("Add Person")(views.html.addPerson(personForm, personList)))
  }

  def submitAddPersonForm = Action { implicit request =>
    personForm.bindFromRequest().fold(
    
      // Failure function:
      (formContainingErrors: Form[Person]) => {
        // Show the user a completed form with error messages:
        val personList = PersonDal.getPersons()
        BadRequest(views.html.addPerson(formContainingErrors, personList))
      },

      // Success function:
      (person: Person) => {
 
        val newId = PersonDal.createPerson(person)

        // Save `todo` to a database and redirect:
        Redirect(routes.AddPersonController.showForm)
      }
    )
  }  
  
}