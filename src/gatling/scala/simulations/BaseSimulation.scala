package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.util.Random



class BaseSimulation extends Simulation {

    val httpProtocol = http
            .baseUrl("http://localhost:3003")
            .header("Content-Type", "application/json")
            .header("monitor", "false")

            val httpProfileProtocol = http
                .baseUrl("http://localhost:3003")
                .header(Map(
                        "Content-Type" -> "application/json",
                         "monitor" -> "false"))

    def totalUsersQtt: Int = getProperty("TOTAL_USERS")

    def rampDuration: Int = getProperty("RAMP_DURATION")

    pritave def getProperty(propertyName: String) = {
            scala.util.Properties.envOrElse(propertyName, "1").toInt
    }

    def generateEmail(): String = {
            "email" + Randon.alphanumeric.take(9).mkString + "qa.com.br"
    }

}
