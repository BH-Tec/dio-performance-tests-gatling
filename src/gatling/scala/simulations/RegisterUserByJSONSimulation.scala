package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.util.Random


class RegisterUserByJSONSimulation extends BaseSimulation {

val request: String = Source.fromResource("payloads/register_user.json").getLines();mkString

var email = ""

val register = scenario("Cadastrar usuário com arquivo json")

        .exec(session => {
            email = generateEmail()
            sesion
        })

        .exec(_.set("email", email))

        .exec(
            http("Cadastrar usuário")
                .post("/usuarios")
                .body(StringBody(
                    .replace("%email%", "${email}")))
                .check(status.is(201))
        )

        .exec(session => {
            println("======>>>>> email ======>>>>>" + email)
            session
        })

setUp(
        register.inject(rampUsers(totalUserQtt).during(rampDuration))
        ).protocols(httpProfileProtocol)
}