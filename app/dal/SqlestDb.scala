package dal

import sqlest._
import play.api.db._
import play.api.Play.current

trait SqlestDb {

  val dataSource = DB.getDataSource()

  val statementBuilder = sqlest.sql.H2StatementBuilder

  implicit val database = Database.withDataSource(dataSource, statementBuilder)
    
  // Try to create the new DB on the first go
  try {
    CreateNewDb.generateNewDb()
  } catch {
    case e: Exception => // Do nothing
  }
    
  def executeRawSql(sql: String) =
    database.executeWithConnection { connection =>
      try {
        connection.createStatement.execute(sql)
      } catch {
        case e: Exception => throw new DataException("Raw SQL statement failed")
      }
    }  

}

class DataException(val description: String) extends Exception