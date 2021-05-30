package com.ex2.jobs.auth

import com.ex2.jobs.auth.repo.SessionRepository
import com.ex2.jobs.context.PoriSession
import com.ex2.jobs.security.UserRoles
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SessionService {

    @Autowired
    private lateinit var sessionRepo: SessionRepository

    @Autowired
    private lateinit var roleService: RoleService

    fun authorizeToken(token: String): Long? {
        val result = sessionRepo.findById(token)
        return result.orElse(null)?.memberId
    }

    fun validateToken(token: String?): PoriSession {

        val result = if (token.isNullOrEmpty()) null else sessionRepo.findById(token)
        val memberId = result?.orElse(null)?.memberId
        val role = if (memberId == null) UserRoles.ROLE_VISITOR else roleService.getRole(memberId)

        return PoriSession(
            token = token,
            memberId = memberId,
            role = role
        )
    }
}