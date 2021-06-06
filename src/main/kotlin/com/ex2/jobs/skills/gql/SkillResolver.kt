package com.ex2.jobs.skills.gql

import com.ex2.jobs.context.PoriRequestUtils
import com.ex2.jobs.error.ExceptionFactory
import com.ex2.jobs.gen.DgsConstants
import com.ex2.jobs.gen.types.*
import com.ex2.jobs.jobs.service.JobService
import com.ex2.jobs.security.ApplicantOnly
import com.ex2.jobs.security.EmployerOnly
import com.ex2.jobs.skills.model.SkillEntity
import com.ex2.jobs.skills.service.SkillService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class SkillResolver {

    @Autowired
    private lateinit var skillService: SkillService

    @Autowired
    private lateinit var requestUtils: PoriRequestUtils

    @Autowired
    private lateinit var jobService: JobService

    @DgsData(
        parentType = DgsConstants.APPLICANTPROFILE.TYPE_NAME,
        field = DgsConstants.APPLICANTPROFILE.Skills
    )
    fun skillsOfApplicant(dfe: DataFetchingEnvironment): List<Skill> {
        val memberId = dfe.getSource<ApplicantProfile>().id
        return skillService.skillsOfApplicant(memberId)
            .map { it.toGraph() }
    }

    @DgsData(
        parentType = DgsConstants.JOB.TYPE_NAME,
        field = DgsConstants.JOB.Skills
    )
    fun skillsOfJob(dfe: DataFetchingEnvironment): List<Skill> {
        val memberId = dfe.getSource<Job>().id
        return skillService.getSkillsOfJob(memberId)
            .map { it.toGraph() }
    }

    // --- mapping ---

    @EmployerOnly
    @DgsMutation(field = DgsConstants.MUTATION.MapSkillToJob)
    fun mapSkillsToJob(
        @InputArgument("data") data: JobSkillsInput
    ): List<Skill> {
        val employerId = requestUtils.employerIdOrThrow()
        val job = jobService.getJob(data.jobId)
        if (job?.postedBy != employerId) {
            throw ExceptionFactory.plain("This job cannot be updated")
        }

        val list = data.skills.map { it.toEntity() }.toSet()
        return skillService.saveSkillsForJob(data.jobId, list).map {
            it.toGraph()
        }
    }

    @ApplicantOnly
    @DgsMutation(field = DgsConstants.MUTATION.MapSkillToUser)
    fun mapSkillsToUser(
        @InputArgument("data") data: UserSkillsInput
    ): List<Skill> {
        val memberId = requestUtils.applicantIdOrThrow()
        val list = data.skills.map { it.toEntity() }.toSet()
        return skillService.saveSkills(memberId = memberId.toString(), list).map {
            it.toGraph()
        }
    }
}

private fun SkillEntity.toGraph() = Skill(
    id = id!!,
    name = name,
    description = description
)

private fun SkillInput.toEntity() = SkillEntity(
    id = id,
    name = name,
    description = description
)
