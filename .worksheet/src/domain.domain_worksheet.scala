package domain

import uk.co.benmulhern.mealserver.domain._

object domain_worksheet {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(188); 

  
  val matthew = new Person(1,"Matthew","Rodgerson","matthew.rodgerson@jhc.co.uk",Tuckers.sausage);System.out.println("""matthew  : uk.co.benmulhern.mealserver.domain.Person = """ + $show(matthew ));$skip(80); 
  val ben = new Person(1,"Ben","Mulhern","ben.mulhern@jhc.co.uk",Tuckers.bacon);System.out.println("""ben  : uk.co.benmulhern.mealserver.domain.Person = """ + $show(ben ));$skip(72); 
  
  val order1 = List((ben,Tuckers.sausage),(matthew,Tuckers.sausage));System.out.println("""order1  : List[(uk.co.benmulhern.mealserver.domain.Person, uk.co.benmulhern.mealserver.domain.Sandwich)] = """ + $show(order1 ));$skip(69); 
  val order2 = List((ben,ben.usualItem),(matthew,matthew.usualItem));System.out.println("""order2  : List[(uk.co.benmulhern.mealserver.domain.Person, uk.co.benmulhern.mealserver.domain.Sandwich)] = """ + $show(order2 ));$skip(39); val res$0 = 
  
  Tuckers.orderSandwiches(order1,0);System.out.println("""res0: Double = """ + $show(res$0));$skip(36); val res$1 = 
  Tuckers.orderSandwiches(order2,0);System.out.println("""res1: Double = """ + $show(res$1))}
}
