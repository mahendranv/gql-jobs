package com.ex2.jobs.skills

import com.ex2.jobs.auth.entities.AuthEntity
import com.ex2.jobs.auth.repo.AuthRepository
import com.ex2.jobs.skills.model.SkillEntity
import com.ex2.jobs.skills.model.SkillRepo
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class SkillRepoTest {

    @Autowired
    private lateinit var skillRepo: SkillRepo

    @Autowired
    private lateinit var userRepo: AuthRepository

    @Test
    fun testInjectionIsNotNull() {
        assertThat(skillRepo).isNotNull()
        assertThat(userRepo).isNotNull()
    }

    @Test
    fun `Inserted skill contains id`() {
        val result = skillRepo.save(
            SkillEntity(
                name = "Android"
            )
        )
        assertNotEquals(null, result.id, "ID is created")

        val iOS = skillRepo.save(
            SkillEntity(
                name = "iOS"
            )
        )
        assertNotEquals(result.id, iOS.id, "ID not unique")
    }

    @Test
    fun `Duplicate id not generated`() {
        skillRepo.save(SkillEntity(name = "Android"))
        assertThrows<Exception> { skillRepo.save(SkillEntity(name = "andRoid", description = "Android is a OS")) }
    }

    fun prepareSkills(): MutableList<SkillEntity> {
        return skillRepo.saveAll(listOf(SkillEntity(name = "Android"), SkillEntity(name = "iOS")))
    }

    @Test
    fun `User mapping successful`() {
        val skills = prepareSkills()
        userRepo.save(
            AuthEntity(
                email = "foo@bar.com",
                password = "pass",
                tags = skills
            )
        )

        val users = userRepo.matchingUsers(setOf("android"))
        assertEquals(1, users.size, "User mapped to skill ")
    }

}