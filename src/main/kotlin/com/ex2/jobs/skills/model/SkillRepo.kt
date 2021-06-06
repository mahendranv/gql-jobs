package com.ex2.jobs.skills.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SkillRepo : JpaRepository<SkillEntity, String> {

    @Query("select * from skills s where e.key in (:keys)", nativeQuery = true)
    fun listMatchingTags(@Param("keys") keys: Set<String>): List<SkillEntity>

    @Query(
        "select * from skills s RIGHT JOIN user_skill_map usm on usm.skill_id=s.id where usm.user_id=(:userId);",
        nativeQuery = true
    )
    fun listSkillsOfUser(@Param("userId") userId: String): List<SkillEntity>

    @Query("select * from skills s WHERE s.key in (:keys) ORDER BY name;", nativeQuery = true)
    fun listSkillsOfKeys(@Param("keys") keys: Set<String>): List<SkillEntity>


    @Query(
        "select * from skills s RIGHT JOIN job_skill_map jsm on jsm.skill_id=s.id where jsm.job_id=(:jobId);",
        nativeQuery = true
    )
    fun listSkillsOfJob(@Param("jobId") jobId: String): List<SkillEntity>

}