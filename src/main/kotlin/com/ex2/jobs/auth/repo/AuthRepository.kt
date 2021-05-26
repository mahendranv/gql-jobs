package com.ex2.jobs.auth.repo

import com.ex2.jobs.auth.entities.AuthEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AuthRepository : JpaRepository<AuthEntity, Int>