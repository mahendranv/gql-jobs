package com.ex2.jobs.auth

import com.ex2.jobs.auth.entities.RoleEntity
import com.ex2.jobs.auth.repo.AuthRepository
import com.ex2.jobs.auth.repo.RoleRepository
import com.ex2.jobs.error.ExceptionFactory
import com.ex2.jobs.security.UserRoles
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
            throw ExceptionFactory.plain("Member - not present in our system")
        }
    }

    fun getRole(memberId: Long): UserRoles {
        val entity = roleRepository.findById(memberId).orElseThrow { Exception("Member not found") }
        return entity.role
    }

}