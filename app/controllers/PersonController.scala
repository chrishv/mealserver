package controllers

import domain.Person
import dal.PersonDal

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._

class PersonController extends Controller {

  val personDal = new PersonDal {}

  val personForm: Form[Person] = 
    Form(mapping(
           "forename" -> nonEmptyText,
           "surname"  -> nonEmptyText,
           "email"    -> nonEmptyText
         )(Person.formApply)(Person.formUnapply))

  def showPersons = Action {
    val searchTerm = ""
    val personList = personDal.getPersons(searchTerm)
    Ok(views.html.main("MealServer - persons")(views.html.persons(personForm, "", personList)))

  }

  def searchPersons(searchTerm: String) = Action {
    val personList = personDal.getPersons(searchTerm)
    Ok(views.html.main("MealServer - persons")(views.html.persons(personForm, searchTerm, personList)))
  }
  
  def submitAddPersonForm = Action { implicit request =>
    personForm.bindFromRequest().fold(
    
      // Failure function:
      (formContainingErrors: Form[Person]) => {
        // Show the user a completed form with error messages:

        val searchTerm = ""
        val personList = personDal.getPersons(searchTerm)
        BadRequest(views.html.main("MealServer - persons")(views.html.persons(formContainingErrors, searchTerm, personList)))

      },

      // Success function:
      (person: Person) => {
 
        val newId = personDal.createPerson(person)
        Redirect(routes.PersonController.showPersons)
      }
    )
  }  
  
  def deletePerson(personId: Int) = Action {

    personDal.deletePerson(personId)
    Redirect(routes.PersonController.showPersons)

  }

}