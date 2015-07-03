package dal

import sqlest._
import play.api.db._
import play.api.Play.current
import com.ibm.as400.access.AS400JDBCConnectionPoolDataSource

trait SqlestDb {

  //val dataSource = DB.getDataSource()
    val dataSource = {
    val dataSource = new AS400JDBCConnectionPoolDataSource("tracey.servers.jhc.co.uk")
    dataSource.setUser("RTUSER")
    dataSource.setPassword("RTUSER")
    dataSource.setLibraries("W.MJR")
    dataSource.setNaming("system")
    dataSource.setPrompt(false)
    dataSource
  }
  //val statementBuilder = sqlest.sql.H2StatementBuilder
    val statementBuilder = sqlest.sql.DB2StatementBuilder
  
  //  implicit val database = Database.withDataSource(dataSource, statementBuilder)
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