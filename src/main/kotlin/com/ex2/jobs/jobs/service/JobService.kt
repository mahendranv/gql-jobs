package com.ex2.jobs.jobs.service

import com.ex2.jobs.jobs.entity.JobEntity
import com.ex2.jobs.jobs.entity.JobRepository
import com.ex2.jobs.jobs.entity.JobStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JobService {

    @Autowired
    private lateinit var jobRepo: JobRepository

    fun saveJob(jobEntity: JobEntity): JobEntity {
        return jobRepo.save(jobEntity)
    }

    fun approveJob(jobId: Long) {
        jobRepo.updateJobStatus(jobId, JobStatus.OPEN)
    }

    fun closeJob(jobId: Long) {
        jobRepo.updateJobStatus(jobId, JobStatus.CLOSE)
    }
}