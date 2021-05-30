package com.ex2.jobs.auth.repo

import com.ex2.jobs.auth.entities.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<RoleEntity, Int>