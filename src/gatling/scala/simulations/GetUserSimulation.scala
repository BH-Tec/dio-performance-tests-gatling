package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.util.Random

class GetUserSimulation extends BaseSimulation {

    val userFeed = csv("payloads/user.csv").circular

    def getUser(): ChainBuilder = {
            feed(userFeed)
                    .exec(
                        http("Busca de Usuário")
                        .get("/usuarios?email=${Email}")
                        .check(status.is(200))
                        .check(jsonPath("$").saveAs("body"))
                        .check(jsonPath("$.usuario[0].email").saveAs("email"))
                    )

                    .exec{ session =>
                        println("Body: --> " + session("body").as[String].trim)
                        println("Email: --> " + session("email").as[String].trim)
                        session
                    }
        }

        val registerScn: ScenarioBuilder = scenario("Busca de usuário").exec(getUser())

setUp(
        register.inject(rampUsers(totalUserQtt).during(rampDuration))
).protocols(httpProfileProtocol)

}