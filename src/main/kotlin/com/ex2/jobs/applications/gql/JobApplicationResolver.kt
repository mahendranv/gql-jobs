package com.ex2.jobs.applications.gql

import com.ex2.jobs.adapters.toEntity
import com.ex2.jobs.adapters.toGraph
import com.ex2.jobs.applications.model.JobApplicationEntity
import com.ex2.jobs.applications.service.JobApplicationService
import com.ex2.jobs.context.PoriRequestUtils
import com.ex2.jobs.error.ExceptionFactory
import com.ex2.jobs.gen.DgsConstants
import com.ex2.jobs.gen.types.JobApplication
import com.ex2.jobs.gen.types.JobApplicationInput
import com.ex2.jobs.jobs.service.JobService
import com.ex2.jobs.security.ApplicantOnly
import com.ex2.jobs.security.UserRoles
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class JobApplicationResolver {

    @Autowired
    private lateinit var requestUtils: PoriRequestUtils

    @Autowired
    private lateinit var jobApplicationService: JobApplicationService

    @Autowired
    private lateinit var jobService: JobService

    @ApplicantOnly
    @DgsData(
        parentType = DgsConstants.Mutation_TYPE,
        field = DgsConstants.MUTATION.ApplyForJob
    )
    fun applyForJob(@InputArgument("data") data: JobApplicationInput): JobApplication {
        val entity = data.toEntity(applicantId = requestUtils.applicantIdOrThrow().toString())

        if (jobService.getJob(data.jobId) == null) {
            throw ExceptionFactory.plain("Invalid job")
        }
        // TODO: Loaders for profile expansion
        return jobApplicationService.applyForJob(entity).toGraph()
    }


    @DgsData(
        parentType = DgsConstants.QUERY_TYPE,
        field = DgsConstants.QUERY.ViewApplication
    )
    fun viewJobApplication(@InputArgument("id") id: String): JobApplication {
        val session =
            requestUtils.getSessionData() ?: throw ExceptionFactory.plain("You must login to view the application")

        val probe = JobApplicationEntity(
            id = id,
            jobId = null,
            applicantId = null
        )

        // Applicant
        if (session.role == UserRoles.ROLE_APPLICANT) {
            probe.applicantId = session.memberId!!.toString()
        }
        val result = jobApplicationService.viewApplication(probe).toGraph()
        if (session.role == UserRoles.ROLE_EMPLOYER) {
            val job = jobService.getJob(result.jobId)
            if (job?.postedBy != session.memberId!!) {
                throw ExceptionFactory.plain("This job is not posted from current employer account")
            }
        } else {
            throw ExceptionFactory.plain("Unauthorized access")
        }
        return result
    }

    @ApplicantOnly
    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.ViewMyapplications)
    fun viewMyApplications(): List<JobApplication> {
        val probe = JobApplicationEntity(
            applicantId = requestUtils.applicantIdOrThrow().toString(),
            jobId = null,
            id = null
        )
        return jobApplicationService.listApplications(probe).map { it.toGraph() }
    }
}