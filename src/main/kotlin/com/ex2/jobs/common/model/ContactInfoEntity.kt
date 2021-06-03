package com.ex2.jobs.common.model

import javax.persistence.*

@Entity(name = "contact")
data class ContactInfoEntity(

    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "phone")
    val phone: String? = null,

    @Column(name = "email")
    val email: String? = null
)