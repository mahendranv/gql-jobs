package com.ex2.jobs.auth.entities

import javax.persistence.*

@Entity(name = "auth")
data class AuthEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: String? = null,
    @Column(name = "email", unique = true)
    val email: String,
    @Column(name = "password")
    val password: String
)