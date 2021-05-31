package com.ex2.jobs.jobs

import com.ex2.jobs.gen.types.Job
import com.ex2.jobs.gen.types.JobInput
import com.ex2.jobs.gen.types.JobStatus
import com.ex2.jobs.jobs.entity.JobEntity

object JobTypeAdapter {

    fun JobInput.toEntity(
        postedBy: Long
    ) = JobEntity(
        title = title,
        summary = summary,
        designation = designation,
        isRemote = isRemote,
        jobStatus = jobStatus.toEntity(),
        location = location,
        minExperience = minExperience,
        postedBy = postedBy,
        id = id?.toLong()
    )


    fun JobEntity.toGraph() =
        Job(
            id = id.toString(),
            title = title,
            summary = summary,
            jobStatus = jobStatus.toGraph(),
            postedBy = postedBy.toInt(), //TODO: Add custom scalars
            isRemote = isRemote,
            minExperience = minExperience,
            location = location,
            designation = designation,
            created = created
        )

    fun JobStatus.toEntity() = com.ex2.jobs.jobs.entity.JobStatus.valueOf(name)

    fun com.ex2.jobs.jobs.entity.JobStatus.toGraph() = JobStatus.valueOf(name)
}