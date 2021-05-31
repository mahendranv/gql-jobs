package com.ex2.jobs.jobs.gql

import com.ex2.jobs.context.PoriRequestUtils
import com.ex2.jobs.gen.DgsConstants
import com.ex2.jobs.gen.types.Job
import com.ex2.jobs.gen.types.JobInput
import com.ex2.jobs.jobs.JobTypeAdapter.toEntity
import com.ex2.jobs.jobs.JobTypeAdapter.toGraph
import com.ex2.jobs.jobs.service.JobService
import com.ex2.jobs.security.EmployerOnly
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class JobsResolver {

    @Autowired
    private lateinit var requestUtils: PoriRequestUtils

    @Autowired
    private lateinit var jobService: JobService

    @EmployerOnly
    @DgsData(
        parentType = DgsConstants.Mutation_TYPE,
        field = DgsConstants.MUTATION.SaveJob
    )
    fun saveJob(
        @InputArgument("job") input: JobInput
    ): Job {
        val memberId = requestUtils.employerIdOrThrow()
        val entity = input.toEntity(
            postedBy = memberId
        )
        return jobService.saveJob(entity).toGraph()
    }


    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.GetJobs)
    fun getJobs() : List<Job> {
        return jobService.getJobs().map {
            it.toGraph()
        }
    }
}