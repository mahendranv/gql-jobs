package com.ex2.jobs.security

import com.ex2.jobs.context.PoriRequestUtils
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Aspect
class AuthorizationAspect {

    @Autowired
    private lateinit var requestUtils: PoriRequestUtils

    @Before("@annotation(AdminOnly)")
    fun authenticateRequest(joinPoint: JoinPoint) {

        val session = requestUtils.getSessionData()
        if (session?.hasActiveSession == true) {
//            val result = joinPoint.proceed()
//            return result
        } else {
            // TODO: Error
            throw RuntimeException("This resource is admin only")
//            val result = joinPoint.proceed()
//            return result
        }
    }

}