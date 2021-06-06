package com.ex2.jobs.skills.model

import com.ex2.jobs.auth.entities.AuthEntity
import com.ex2.jobs.jobs.entity.JobEntity
import javax.persistence.*

@Entity(name = "skills")
data class SkillEntity(

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: String? = null,

    @Column(name = "name")
    val name: String,

    @Column(name = "key", unique = true)
    var key: String = name.lowercase(),

    @Column(name = "description")
    val description: String? = null,

    @Column(name = "job_count")
    val jobCount: Int = 0,

    @Column(name = "user_count")
    val userCount: Int = 0,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "job_skill_map",
        joinColumns = [JoinColumn(name = "skill_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "job_id", referencedColumnName = "id")]
    )
    var jobs: List<JobEntity>? = null,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "user_skill_map",
        joinColumns = [JoinColumn(name = "skill_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
    )
    var users: List<AuthEntity>? = null

) {

    @PrePersist
    @PreUpdate
    fun updateKey() {
        key = name.lowercase()
    }
}