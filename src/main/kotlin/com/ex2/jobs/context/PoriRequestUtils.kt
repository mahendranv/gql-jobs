package com.ex2.jobs.context

import com.ex2.jobs.auth.SessionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import javax.servlet.http.HttpServletRequest

/**
 * Responsible for extracting meaningful session information from headers
 */
@Component
class PoriRequestUtils {

    @Autowired
    private lateinit var sessionService: SessionService

    fun saveSessionData(request: HttpServletRequest) {
        val authToken = request.getHeader(HeaderConstants.X_AUTHORIZATION)
        // TODO: Update user & device info

        val session =
            if (authToken == null) null
            else sessionService.validateToken(authToken)
        request.setAttribute(
            PORI_SESSION, PoriSession(
                token = authToken,
                memberId = session?.memberId
            )
        )
        println("PoriRequestUtils.saveSessionData : $request")
    }

    fun getSessionData(): PoriSession? {
        val session = RequestContextHolder
            .getRequestAttributes()?.getAttribute(PORI_SESSION, RequestAttributes.SCOPE_REQUEST)
        return session as? PoriSession?
    }

    companion object {

        const val PORI_SESSION = "pori_session"

    }
}