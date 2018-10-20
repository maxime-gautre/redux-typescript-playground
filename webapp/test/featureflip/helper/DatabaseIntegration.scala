package featureflip.helper

import java.util.TimeZone

import scala.util.Try

import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, Outcome, fixture}
import play.api.db.{Database, Databases}
import play.api.db.evolutions.Evolutions

trait DatabaseIntegration
    extends fixture.WordSpec
    with BeforeAndAfterEach
    with BeforeAndAfterAll {

  val conf = ConfigFactory.load()

  type FixtureParam = Database

  // To override when inheriting
  // SQL statements to run before each test
  def beforeEachStmt: String

  private val database: Database = Databases(
    name = conf.getString("db.featureflip.name"),
    driver = conf.getString("db.featureflip.driver"),
    url =
      s"jdbc:postgresql://${conf.getString("db.featureflip.host")}:${conf.getString(
        "db.featureflip.port")}/${conf.getString("db.featureflip.name")}",
    config = Map(
      "username" -> conf.getString("db.featureflip.username"),
      "password" -> conf.getString("db.featureflip.password")
    )
  )

  protected def withDatabase[T](block: Database => T): T = {
    block(database)
  }

  override def beforeEach(): Unit = {
    withDatabase { db =>
      // Cleanup DB model and data
      Evolutions.applyEvolutions(db)
    }
  }

  override def withFixture(test: OneArgTest): Outcome = {
    // Set default zone to UTC
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))

    withDatabase { db =>
      // Apply data fixtures
      db.withTransaction { implicit conn =>
        conn.prepareStatement(beforeEachStmt).execute()
      }
      // Test
      val outcome = Try(test(db))
      // Return
      outcome.get
    }
  }

  override def afterEach(): Unit = {
    withDatabase { db =>
      // Cleanup DB
      Evolutions.cleanupEvolutions(db)
    }
  }

  override def afterAll(): Unit = {
    super.afterAll()
    database.shutdown()
  }
}
