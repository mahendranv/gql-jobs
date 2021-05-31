package com.ex2.jobs.jobs.service

import com.ex2.jobs.error.ExceptionFactory
import com.ex2.jobs.jobs.entity.JobEntity
import com.ex2.jobs.jobs.entity.JobRepository
import com.ex2.jobs.jobs.entity.JobStatus
import graphql.GraphQLException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JobService {

    @Autowired
    private lateinit var jobRepo: JobRepository

    fun saveJob(jobEntity: JobEntity): JobEntity {
        return if (jobEntity.id == null) {
            jobRepo.save(
                jobEntity.copy(
                    jobStatus = JobStatus.CREATED
                )
            )
        } else {
            updateJob(jobEntity)
        }
    }

    fun updateJob(updated: JobEntity): JobEntity {
        val job = jobRepo.findById(updated.id!!).orElseThrow {
            ExceptionFactory.plain("Job is not found")
        }

        // Access control
        if (job.postedBy != updated.postedBy) {
            throw ExceptionFactory.plain("This job cannot be updated from this employer account")
        } else if (job.jobStatus == JobStatus.CREATED) {
            if (updated.jobStatus == JobStatus.OPEN) {
                throw ExceptionFactory.plain("Employer cannot mark job open")
            }
        }

        return jobRepo.save(updated)
    }

    fun approveJob(jobId: Long) {
        jobRepo.updateJobStatus(jobId, JobStatus.OPEN)
    }

    fun closeJob(jobId: Long) {
        jobRepo.updateJobStatus(jobId, JobStatus.CLOSE)
    }
}