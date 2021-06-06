package com.ex2.jobs.skills.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity(name = "job_skill_map")
@IdClass(JobSkillMap::class)
data class JobSkillMap(

    @Id
    @Column(name = "job_id")
    val jobId: String,

    @Id
    @Column(name = "skill_id")
    val skillId: String

) : Serializable


@Repository
interface JobSkillRepo : JpaRepository<JobSkillMap, String>