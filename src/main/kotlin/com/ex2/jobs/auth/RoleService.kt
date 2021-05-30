package com.ex2.jobs.auth

import com.ex2.jobs.auth.entities.RoleEntity
import com.ex2.jobs.auth.repo.AuthRepository
import com.ex2.jobs.auth.repo.RoleRepository
import com.ex2.jobs.security.UserRoles
import graphql.GraphQLException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RoleService {

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var authRepository: AuthRepository

    fun assignRole(memberId: Long, role: UserRoles) {
        val member = authRepository.findById(memberId.toString())
        if (member.isPresent) {
            roleRepository.save(RoleEntity(memberId, role))
        } else {
            throw GraphQLException("Member - not present in our system")
        }
    }

}