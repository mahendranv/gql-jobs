package com.ex2.jobs.applications.service

import com.ex2.jobs.applications.model.JobApplicationEntity
import com.ex2.jobs.applications.model.JobApplicationRepo
import com.ex2.jobs.error.ExceptionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service

@Service
class JobApplicationService {

    @Autowired
    private lateinit var repo: JobApplicationRepo

    fun applyForJob(application: JobApplicationEntity): JobApplicationEntity {
        val isAlreadyApplied = repo.findAll(
            Example.of(
                JobApplicationEntity(
                    id = null,
                    applicantId = application.applicantId,
                    jobId = application.jobId
                )
            )
        ).isNotEmpty()

        if (isAlreadyApplied) {
            throw ExceptionFactory.plain("Already applied for this job")
        }

        // TODO: Max applicants threshold reached
        // TODO: Auto close job

        return repo.save(application)
    }

    fun viewApplication(probe: JobApplicationEntity): JobApplicationEntity {
        return repo.findOne(Example.of(probe))
            .orElseThrow { ExceptionFactory.plain("Cannot find the job application") }
    }

    fun listApplications(probe: JobApplicationEntity): List<JobApplicationEntity> {
        return repo.findAll(Example.of(probe))
    }
}