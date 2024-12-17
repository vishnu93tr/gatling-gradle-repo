package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

/**
 * @author vishnuvardhan
 * @Date 17/12/24
 */
public class BasicSimulation extends Simulation {
    // HTTP Configuration
    HttpProtocolBuilder httpProtocol = http.baseUrl("https://jsonplaceholder.typicode.com")
            .acceptHeader("application/json");

    // Scenario Definition
    ScenarioBuilder scn = scenario("Basic Simulation")
            .exec(HttpDsl.http("Get Posts")
                    .get("/posts/1")
                    .check(status().is(200)))
            .pause(1);

    {
        // Load Injection Profile
        setUp(
                scn.injectOpen(
                        atOnceUsers(10),                 // Start 10 users at once
                        rampUsers(50).during(30)         // Ramp up 50 users over 30 seconds
                )
        ).protocols(httpProtocol);
    }
}
