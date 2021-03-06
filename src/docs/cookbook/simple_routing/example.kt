package cookbook.simple_routing

import org.http4k.core.Method.DELETE
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes

/**
 * This example shows how to use the simple routing functionality to bind several routes
 */
fun main(args: Array<String>) {

    val app = routes(
        "bob" to GET bind { Response(OK).body("you GET bob") },
        "rita" to POST bind { Response(OK).body("you POST rita") },
        "sue" to DELETE bind { Response(OK).body("you DELETE sue") }
    )

    println(app(Request(GET, "/bob")))
    println(app(Request(POST, "/bob")))
    println(app(Request(DELETE, "/sue")))
}
