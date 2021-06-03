package com.ex2.jobs.context

import com.ex2.jobs.auth.SessionService
import com.ex2.jobs.error.ExceptionFactory
import com.ex2.jobs.security.UserRoles
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

        val session = sessionService.validateToken(authToken)
        request.setAttribute(PORI_SESSION, session)

        println("PoriRequestUtils.saveSessionData $session")
    }

    fun getSessionData(): PoriSession? {
        val session = RequestContextHolder
            .getRequestAttributes()?.getAttribute(PORI_SESSION, RequestAttributes.SCOPE_REQUEST)
        return session as? PoriSession?
    }

    fun memberIdOrThrow(): Long {
        val data = getSessionData()
        return data?.memberId ?: throw ExceptionFactory.plain("Please login")
    }

    fun employerIdOrThrow(): Long {
        val data = getSessionData()
        if (data?.role == UserRoles.ROLE_EMPLOYER && data.memberId != null) {
            return data.memberId!!
        }
        throw Exception("Employer session not found")
    }


    fun applicantIdOrThrow(): Long {
        val data = getSessionData()
        if (data?.role == UserRoles.ROLE_APPLICANT && data.memberId != null) {
            return data.memberId!!
        }
        throw Exception("Applicant session not found")
    }

    companion object {

        const val PORI_SESSION = "pori_session"

    }
}