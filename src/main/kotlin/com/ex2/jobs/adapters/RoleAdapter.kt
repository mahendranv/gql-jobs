package com.ex2.jobs.adapters

import com.ex2.jobs.auth.entities.RoleEntity
import com.ex2.jobs.gen.types.RoleInput
import com.ex2.jobs.security.UserRoles

object RoleAdapter {

    fun RoleInput.toEntity() =
        RoleEntity(
            memberId = memberId.toLong(),
            role = role.toPoriRole()
        )

    fun com.ex2.jobs.gen.types.UserRoles.toPoriRole() = UserRoles.valueOf(name)
}