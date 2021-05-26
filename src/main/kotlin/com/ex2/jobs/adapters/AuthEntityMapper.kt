package com.ex2.jobs.adapters

import com.ex2.jobs.auth.entities.AuthEntity
import com.ex2.jobs.gen.types.Auth

object AuthEntityMapper : GraphEntityMapper<Auth, AuthEntity> {

    override fun toEntity(g: Auth): AuthEntity {
        return AuthEntity(
            id = g.id,
            email = g.email,
            password = g.password
        )
    }

    override fun toGraph(e: AuthEntity): Auth {
        return Auth(
            id = e.id,
            email = e.email,
            password = e.password
        )
    }
}