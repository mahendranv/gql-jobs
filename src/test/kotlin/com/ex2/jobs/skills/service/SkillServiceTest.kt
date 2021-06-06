package com.ex2.jobs.skills.service

import com.ex2.jobs.PoriJobsApplication
import com.ex2.jobs.auth.entities.AuthEntity
import com.ex2.jobs.auth.repo.AuthRepository
import com.ex2.jobs.skills.model.SkillEntity
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
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

    val user = AuthEntity("1", "emp", "pass", null)
    val skills = setOf(
        SkillEntity(
            name = "Android"
        ),
        SkillEntity(
            name = "iOS"
        ),
    )

    private fun prepare() {
        authRepo.save(user)
    }

    @Test
    fun `Skills saved successfully`() {
        prepare()
        val result = sut.saveSkills("1", skills)
        assertEquals(2, result.size)

        val skills = sut.skillsOfApplicant("1")
        assertEquals(2, skills.size)
    }

    @Test
    fun `Skills appended to existing`() {
        prepare()

        val result = sut.saveSkills("1", skills)
        assertEquals(2, result.size)

        sut.saveSkills("1", setOf(SkillEntity(name = "Springboot")))

        val skills = sut.skillsOfApplicant("1")
        assertEquals(3, skills.size)
    }
}