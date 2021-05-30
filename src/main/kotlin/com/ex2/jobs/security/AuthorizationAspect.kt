package com.ex2.jobs.security

import com.ex2.jobs.context.PoriRequestUtils
import graphql.GraphQLException
import org.aspectj.lang.JoinPoint
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
        if (session?.role != UserRoles.ROLE_ADMIN) {

            // TODO: Error
            throw GraphQLException("This resource is admin only")
        }
    }

    @Before("@annotation(EmployerOnly)")
    fun authenticateEmployerOnlyRequest(joinPoint: JoinPoint) {
        val session = requestUtils.getSessionData()
        if (session?.role != UserRoles.ROLE_EMPLOYER) {
            // TODO: Error
            throw GraphQLException("This resource is for employer only")
        }
    }


}