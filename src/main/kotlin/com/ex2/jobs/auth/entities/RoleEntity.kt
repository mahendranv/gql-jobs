package com.ex2.jobs.auth.entities

import com.ex2.jobs.security.UserRoles
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "user_roles")
data class RoleEntity(

    @Id
    @Column(name = "member_id")
    val memberId: Long,

    @Column(name = "role")
    val role: UserRoles

)