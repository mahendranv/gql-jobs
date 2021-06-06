package com.ex2.jobs.skills.gql

import com.ex2.jobs.gen.DgsConstants
import com.ex2.jobs.gen.types.ApplicantProfile
import com.ex2.jobs.gen.types.Job
import com.ex2.jobs.gen.types.Skill
import com.ex2.jobs.skills.model.SkillEntity
import com.ex2.jobs.skills.service.SkillService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class SkillResolver {

    @Autowired
    private lateinit var skillService: SkillService

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

}

private fun SkillEntity.toGraph() = Skill(
    id = id!!,
    key = name,
    description = description
)
