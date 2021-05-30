package com.ex2.jobs.auth.repo

import com.ex2.jobs.auth.entities.SessionEntity
import org.springframework.data.repository.CrudRepository

interface SessionRepository : CrudRepository<SessionEntity, String>