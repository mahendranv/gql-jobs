package com.ex2.jobs.skills.service

import com.ex2.jobs.PoriJobsApplication
import com.ex2.jobs.auth.entities.AuthEntity
import com.ex2.jobs.auth.repo.AuthRepository
import com.ex2.jobs.jobs.entity.JobEntity
import com.ex2.jobs.jobs.entity.JobRepository
import com.ex2.jobs.jobs.entity.JobStatus
import com.ex2.jobs.skills.model.SkillEntity
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [PoriJobsApplication::class]
)
@TestPropertySource(
    locations = ["classpath:application-integrationtest.properties"]
)
internal class SkillServiceTest {

    @Autowired
    private lateinit var sut: SkillService

    @Autowired
    private lateinit var authRepo: AuthRepository

    @Autowired
    private lateinit var jobRepo: JobRepository

    val user = AuthEntity("1", "emp", "pass", null)
    val skills = setOf(
        SkillEntity(name = "Android"),
        SkillEntity(name = "iOS"),
    )

    fun prepareUsers() {
        authRepo.save(user)


        jobRepo.save(
            JobEntity(
                id = 1,
                title = "Senior android dev",
                summary = "Senior android dev who can code backend",
                isRemote = false,
                location = "Bangalore",
                minExperience = 0,
                postedBy = 1,
                designation = "Android",
                jobStatus = JobStatus.OPEN
            )
        )
    }


    @Test
    fun `Skills saved to applicant profile`() {
        prepareUsers()
        val result = sut.saveSkills("1", skills)
        assertEquals(2, result.size)

        val skills = sut.skillsOfApplicant("1")
        assertEquals(2, skills.size)

        // Append few more
        sut.saveSkills("1", setOf(SkillEntity(name = "Springboot")))

        val skills2 = sut.skillsOfApplicant("1")
        assertEquals(3, skills2.size)
    }

    @Test
    fun `Skills saved to job`() {
        prepareUsers()

        val result = sut.saveSkillsForJob("1", skills)
        assertEquals(2, result.size)

        val skills = sut.getSkillsOfJob("1")
        assertEquals(2, skills.size, "Initial set inserted")

        // Append more
        sut.saveSkillsForJob("1", setOf(SkillEntity(name = "Springboot")))

        val skills2 = sut.getSkillsOfJob("1")
        assertEquals(3, skills2.size, "skill appended")

        // Duplicate more
        sut.saveSkillsForJob("1", setOf(SkillEntity(name = "Springboot")))
        val skills3 = sut.getSkillsOfJob("1")
        assertEquals(3, skills3.size, "skill not duplicated")
    }
}