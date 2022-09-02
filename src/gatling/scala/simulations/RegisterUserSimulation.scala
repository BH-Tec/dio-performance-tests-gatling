package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.util.Random

class RegisterUserSimulation extends Simulation {

    object generateRandomString {
        def getString(): String = {
                "email" + Random.alphanumeric.take(9).mkString
        }
    }

    val register = scenario("Cadastrar usuário")
            .exec(
                    http("Cadastrar usuário modo básico")
                            .post("http://localhost:3000/usuarios")
                            .header("Content-Type", "application/json")
                            .header("monitor", "false")
                            .body(
                                    StringBody(_ =>
                                    s"""{
                                    |  "nome": "Bruno",
                                    |  "email": "${generateRandomString.getString()}@qa.com.br",
                                    |  "password": "teste",
                                    |  "administrador": "true,
                                    |}""".stripMargin)).asJson
                            .check(status.is(201))
            )

    setUp(
            register.inject(rampUsers(2).during(2))
    )
}