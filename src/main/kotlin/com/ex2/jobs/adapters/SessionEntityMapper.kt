package com.ex2.jobs.adapters

import com.ex2.jobs.auth.entities.SessionEntity
import com.ex2.jobs.gen.types.Session
import java.lang.RuntimeException

object SessionEntityMapper : GraphEntityMapper<Session, SessionEntity> {

    override fun toEntity(g: Session): SessionEntity {
        throw RuntimeException("No need to implement")
    }

    override fun toGraph(e: SessionEntity): Session {
        return Session(
            memberId = e.memberId.toString(),
            token = e.token
        )
    }
}