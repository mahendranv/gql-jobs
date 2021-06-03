package com.ex2.jobs.applications.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JobApplicationRepo : JpaRepository<JobApplicationEntity, String>