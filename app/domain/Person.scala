package domain

case class Person(val id: Option[Int], val forename: String, val surname: String, val email: String) {

  // Contructor validation
  if (id.isDefined && id.get <=  0 || forename == "" || surname == "" || email == "")
    throw new Exception("Invalid person details")

}

object Person {

  def formApply(forename: String, surname: String, email: String) = new Person(None, forename, surname, email)

  def formUnapply(person: Person): Option[(String, String, String)] = 
    Some((person.forename, person.surname, person.email)) 
  
  
}