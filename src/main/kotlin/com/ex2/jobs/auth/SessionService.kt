package com.ex2.jobs.auth

import com.ex2.jobs.auth.entities.SessionEntity
import com.ex2.jobs.auth.repo.SessionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SessionService {

    @Autowired
    private lateinit var sessionRepo: SessionRepository

    fun validateToken(token: String): SessionEntity? {
        val result = sessionRepo.findById(token)

        return if (result.isPresent) {
            result.get()
        } else {
            null
        }
    }
}