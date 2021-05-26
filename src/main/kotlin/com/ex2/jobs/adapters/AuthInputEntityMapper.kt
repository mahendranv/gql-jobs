package com.ex2.jobs.adapters

import com.ex2.jobs.auth.entities.AuthEntity
import com.ex2.jobs.gen.types.AuthInput
import java.lang.RuntimeException

object AuthInputEntityMapper : GraphEntityMapper<AuthInput, AuthEntity> {

    override fun toEntity(g: AuthInput): AuthEntity = AuthEntity(
        id = null,
        email = g.email,
        password = g.password
    )

    override fun toGraph(e: AuthEntity): AuthInput {
        throw RuntimeException("No need to implement")
    }
}