package com.ex2.jobs.skills.service

import com.ex2.jobs.skills.model.SkillEntity
import com.ex2.jobs.skills.model.SkillRepo
import com.ex2.jobs.skills.model.UserSkillMap
import com.ex2.jobs.skills.model.UserSkillRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SkillService {

    @Autowired
    private lateinit var skillRepo: SkillRepo

    @Autowired
    private lateinit var userSkillRepo: UserSkillRepo

    fun saveSkill(entity: SkillEntity): SkillEntity {
        return skillRepo.save(entity)
    }

    fun skillsOfApplicant(memberId: String): List<SkillEntity> {
        return skillRepo.listSkillsOfUser(memberId)
    }

    private fun createSkillsIfMissing(skills: Set<SkillEntity>): List<SkillEntity> {
        val keys = skills.map { it.key }.toSet()
        val identified = skillRepo.listSkillsOfKeys(keys = keys)
        val identifiedKeys = identified.map { it.key }
        val newSkills = skills.filterNot { skill -> skill.key in identifiedKeys }

        val newlyInsertedSkills = skillRepo.saveAll(newSkills)
        val result = identified.toMutableList()
        result.addAll(newlyInsertedSkills)
        return result
    }

    fun saveSkills(memberId: String, skills: Set<SkillEntity>): List<SkillEntity> {
        val skills = createSkillsIfMissing(skills)

        // Get member skill list
        val mapping = skills.map { UserSkillMap(userId = memberId, skillId = it.id!!) }
        userSkillRepo.saveAll(mapping)

        return skills
    }
}