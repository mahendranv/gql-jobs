package com.ex2.jobs.adapters

import com.ex2.jobs.applications.model.JobApplicationEntity
import com.ex2.jobs.gen.types.JobApplication
import com.ex2.jobs.gen.types.JobApplicationInput

fun JobApplicationInput.toEntity(applicantId: String) =
    JobApplicationEntity(
        id = null,
        applicantId = applicantId,
        jobId = jobId,
        message = message
    )

fun JobApplicationEntity.toGraph() = JobApplication(
    id = id.toString(),
    applicantId = applicantId!!,
    jobId = jobId!!,
    created = created!!,
    message = message
)