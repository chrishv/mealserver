package domain

import uk.co.benmulhern.mealserver.domain._

object domain_worksheet {

  
  val matthew = new Person(1,"Matthew","Rodgerson","matthew.rodgerson@jhc.co.uk",Tuckers.sausage)
                                                  //> matthew  : uk.co.benmulhern.mealserver.domain.Person = Person(1,Matthew,Rodg
                                                  //| erson,matthew.rodgerson@jhc.co.uk,uk.co.benmulhern.mealserver.domain.Sandwic
                                                  //| h@224edc67)
  val ben = new Person(1,"Ben","Mulhern","ben.mulhern@jhc.co.uk",Tuckers.bacon)
                                                  //> ben  : uk.co.benmulhern.mealserver.domain.Person = Person(1,Ben,Mulhern,ben.
                                                  //| mulhern@jhc.co.uk,uk.co.benmulhern.mealserver.domain.Sandwich@68c4039c)
  
  val order1 = List((ben,Tuckers.sausage),(matthew,Tuckers.sausage))
                                                  //> order1  : List[(uk.co.benmulhern.mealserver.domain.Person, uk.co.benmulhern.
                                                  //| mealserver.domain.Sandwich)] = List((Person(1,Ben,Mulhern,ben.mulhern@jhc.co
                                                  //| .uk,uk.co.benmulhern.mealserver.domain.Sandwich@68c4039c),uk.co.benmulhern.m
                                                  //| ealserver.domain.Sandwich@224edc67), (Person(1,Matthew,Rodgerson,matthew.rod
                                                  //| gerson@jhc.co.uk,uk.co.benmulhern.mealserver.domain.Sandwich@224edc67),uk.co
                                                  //| .benmulhern.mealserver.domain.Sandwich@224edc67))
  val order2 = List((ben,ben.usualItem),(matthew,matthew.usualItem))
                                                  //> order2  : List[(uk.co.benmulhern.mealserver.domain.Person, uk.co.benmulhern.
                                                  //| mealserver.domain.Sandwich)] = List((Person(1,Ben,Mulhern,ben.mulhern@jhc.co
                                                  //| .uk,uk.co.benmulhern.mealserver.domain.Sandwich@68c4039c),uk.co.benmulhern.m
                                                  //| ealserver.domain.Sandwich@68c4039c), (Person(1,Matthew,Rodgerson,matthew.rod
                                                  //| gerson@jhc.co.uk,uk.co.benmulhern.mealserver.domain.Sandwich@224edc67),uk.co
                                                  //| .benmulhern.mealserver.domain.Sandwich@224edc67))
  
  Tuckers.orderSandwiches(order1,0)               //> 0.0
                                                  //| 1.23
                                                  //| 2.46
                                                  //| res0: Double = 2.46
  Tuckers.orderSandwiches(order2,0)               //> 0.0
                                                  //| 1.45
                                                  //| 2.6799999999999997
                                                  //| res1: Double = 2.6799999999999997
}