package com.ex2.jobs.jobs.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface JobRepository : JpaRepository<JobEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE jobs j SET j.job_status = :status WHERE j.id = :id", nativeQuery = true)
    fun updateJobStatus(
        @Param("id") id: Long,
        @Param("status") status: JobStatus
    )
}