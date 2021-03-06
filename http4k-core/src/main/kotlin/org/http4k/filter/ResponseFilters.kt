package org.http4k.filter

import org.http4k.core.Filter
import org.http4k.core.Request
import org.http4k.core.Response
import java.time.Clock
import java.time.Duration
import java.time.Duration.between

object ResponseFilters {

    /**
     * Intercept the response after it is sent to the next service.
     */
    fun Tap(fn: (Response) -> Unit) = Filter {
        next ->
        {
            next(it).let {
                fn(it)
                it
            }
        }
    }

    fun ReportLatency(clock: Clock = Clock.systemUTC(), recordFn: (Request, Response, Duration) -> Unit): Filter = Filter {
        next ->
        {
            val start = clock.instant()
            val response = next(it)

            recordFn(it, response, between(start, clock.instant()))
            response
        }
    }
}

