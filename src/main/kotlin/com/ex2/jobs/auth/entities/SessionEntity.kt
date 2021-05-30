package com.ex2.jobs.auth.entities

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "session")
data class SessionEntity(

    // TODO: Token generation logic
    @Id
    @Column(name = "token")
    val token: String,

    @Column(name = "member_id")
    val memberId: Long,

    @Column(name = "valid_till")
    val validity: LocalDateTime

)