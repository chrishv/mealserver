package dal

import sqlest._
import domain.Person


class PersonTable(alias: Option[String]) extends Table("person", alias) {
  val id = column[Int]("person_id")
  val forename = column[String]("forename")
  val surname = column[String]("surname")
  val email = column[String]("email_address")
}

object PersonTable extends PersonTable(None)

object PersonDal extends SqlestDb {

  def getPersons(searchTerm: String) : List[Person] = {
  	// TODO implement searchTerm
    if (searchTerm != "") {
    val wildCardSearch = ("%" + searchTerm + "%")  
    select
  	  .from(PersonTable)
  	  .where((PersonTable.forename like wildCardSearch) || (PersonTable.forename like wildCardSearch) || (PersonTable.email like wildCardSearch))
      .extractAll(personExtractor)}
    else
      select
      .from(PersonTable)
      .extractAll(personExtractor)    
  }

  def createPerson(person: Person): Int = {

  	if (validDbPerson(person))

      database.withTransaction {

        val insertStatement = 
          insert
            .into(PersonTable)
            .values(PersonTable.forename -> person.forename,
                    PersonTable.surname -> person.surname, 
                    PersonTable.email -> person.email).execute
  
        val newId = select(max(PersonTable.id))
                      .from(PersonTable)
                      .fetchHead

         newId match {
          case Some(i) => i
         	case _ => throw new DataException("Database error")
         }

      }     

    else throw new Exception("Invalid person")

  }

  def validDbPerson(person: Person): Boolean = {
    // Just got to check it's not already got an id
    !person.id.isDefined
  }

  def deletePerson(personId: Int) = {
    database.withTransaction {
      delete
        .from(PersonTable)
        .where(PersonTable.id === personId).execute
    }    
  }      

  lazy val personExtractor = extract[Person](
    id = PersonTable.id.asOption,
    forename = PersonTable.forename,
    surname = PersonTable.surname,
    email = PersonTable.email
  )

}

