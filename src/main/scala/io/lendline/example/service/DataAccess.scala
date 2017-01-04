package io.lendline.example.service

import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContextExecutor, Future}

class DataAccess(conf : Configuration, implicit val ec : ExecutionContextExecutor) {
  import Schema._
  import slick.driver.PostgresDriver.api._

  lazy val db = Database.forConfig("database")
  val log = LoggerFactory.getLogger(classOf[DataAccess])

  def findData(user_id : String) : Future[Option[ExampleDataRow]] = {
    log.debug(s"findData $user_id")
    db.run(exampleTable.filter(_.userId === user_id).result.headOption)
  }

  def updateData(row : ExampleDataRow) : Future[ExampleDataRow] = {
    log.debug(s"updateData $row")
    db.run(exampleTable.insertOrUpdate(row)).map(_ => row)
  }
}
