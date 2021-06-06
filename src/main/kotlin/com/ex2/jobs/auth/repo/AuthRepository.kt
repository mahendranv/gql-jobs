package com.ex2.jobs.auth.repo

import com.ex2.jobs.auth.entities.AuthEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AuthRepository : JpaRepository<AuthEntity, String> {
    
    @Query("select * from auth a JOIN skills s WHERE s.key in (:tags);", nativeQuery = true)
    fun matchingUsers(@Param("tags") tags: Set<String>): List<AuthEntity>

}