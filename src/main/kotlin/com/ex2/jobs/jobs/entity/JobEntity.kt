package com.ex2.jobs.jobs.entity

import java.time.LocalDateTime
import java.time.OffsetDateTime
import javax.persistence.*

@Entity(name = "jobs")
data class JobEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(name = "title")
    val title: String,

    @Column(name = "summary")
    val summary: String,

    @Column(name = "is_remote")
    val isRemote: Boolean,

    @Column(name = "location")
    val location: String,

    @Column(name = "created", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    var created: OffsetDateTime? = null,

    @Column(name = "updated", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    var updated: OffsetDateTime? = null,

    @Column(name = "min_experience")
    val minExperience: Int,

    @Column(name = "posted_by")
    val postedBy: Long,

    @Column(name = "designation")
    val designation: String,

    @Column(name = "job_status")
    val jobStatus: JobStatus

) {

    @PrePersist
    fun onCreate() {
        created = OffsetDateTime.now()
    }

    @PreUpdate
    fun onUpdate() {
        updated = OffsetDateTime.now()
    }
}
