package com.ex2.jobs.skills.model

import java.io.Serializable
import javax.persistence.*

@Entity(name = "user_skill_map")
@IdClass(UserSkillMap::class)
data class UserSkillMap(

    @Id
    @Column(name = "user_id")
    val userId: String,

    @Id
    @Column(name = "skill_id")
    val skillId: String
) : Serializable
