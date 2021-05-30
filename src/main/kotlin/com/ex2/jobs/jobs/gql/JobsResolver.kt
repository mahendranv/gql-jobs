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
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import java.lang.Exception

@DgsComponent
class JobsResolver {

    @Autowired
    private lateinit var requestUtils: PoriRequestUtils

    @Autowired
    private lateinit var jobService: JobService

    @EmployerOnly
    @DgsData(
        parentType = DgsConstants.Mutation_TYPE,
        field = DgsConstants.MUTATION.CreateJob
    )
    fun createJob(
        @InputArgument("job") input: JobInput
    ): Job {
        val memberId = requestUtils.getSessionData()?.memberId
            ?: throw Exception("Cannot find a employer session")

        // TODO: DFE get user id
        return jobService.saveJob(
            input.toEntity(
                postedBy = memberId,
                id = null
            )
        ).toGraph()
    }

}