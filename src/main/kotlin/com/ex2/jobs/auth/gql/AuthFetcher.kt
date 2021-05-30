package com.ex2.jobs.auth.gql

import com.ex2.jobs.adapters.AuthEntityMapper
import com.ex2.jobs.adapters.AuthInputEntityMapper
import com.ex2.jobs.adapters.SessionEntityMapper
import com.ex2.jobs.auth.RoleService
import com.ex2.jobs.auth.entities.SessionEntity
import com.ex2.jobs.auth.repo.AuthRepository
import com.ex2.jobs.auth.repo.SessionRepository
import com.ex2.jobs.gen.DgsConstants
import com.ex2.jobs.gen.types.Auth
import com.ex2.jobs.gen.types.AuthInput
import com.ex2.jobs.gen.types.Session
import com.ex2.jobs.security.UserRoles
import com.ex2.jobs.security.Visitor
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import java.time.LocalDateTime
import java.util.*

@DgsComponent
class AuthFetcher {

    @Autowired
    private lateinit var authRepository: AuthRepository

    @Autowired
    private lateinit var sessionRepository: SessionRepository

    @Autowired
    private lateinit var roleService: RoleService

    @DgsData(
        parentType = DgsConstants.MUTATION.TYPE_NAME,
        field = DgsConstants.MUTATION.RegisterUser
    )
    fun registerUser(@InputArgument("auth") auth: AuthInput): Auth {
        val result = authRepository.save(AuthInputEntityMapper.toEntity(auth))
        roleService.assignRole(result.id!!.toLong(), UserRoles.ROLE_APPLICANT)
        return AuthEntityMapper.toGraph(result)
    }

    private fun createNewSession(memberId: String): SessionEntity {

        val validity = LocalDateTime.now().plusDays(5)

        return SessionEntity(
            token = generateToken(memberId),
            memberId = memberId.toLong(),
            validity = validity
        )
    }

    // TODO: Work on token generation
    private fun generateToken(memberId: String): String {
        val content = "$memberId-${System.currentTimeMillis()}-${UUID.randomUUID()}"
        return content
    }

    @Visitor
    @DgsData(
        parentType = DgsConstants.QUERY_TYPE,
        field = DgsConstants.QUERY.Login
    )
    fun loginUser(@InputArgument("auth") auth: AuthInput): Session? {
        val result = authRepository.findAll(
            Example.of(
                AuthInputEntityMapper.toEntity(auth)
            )
        )

        if (result.size == 1) {
            val session = sessionRepository.save(
                createNewSession(result[0].id!!)
            )
            return SessionEntityMapper.toGraph(session)
        } else {
            throw RuntimeException("User account does not exist")
        }
    }
}