package com.ex2.jobs.applications.model

import java.time.OffsetDateTime
import javax.persistence.*

@Entity(name = "job_application")
data class JobApplicationEntity(

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: String?,

    @Column(name = "applicant_id")
    var applicantId: String?,

    @Column(name = "job_id")
    var jobId: String?,

    @Column(name = "created", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    var created: OffsetDateTime? = null,

    @Column(name = "message")
    val message: String? = null
) {

    @PrePersist
    fun onCreate() {
        created = OffsetDateTime.now()
    }

}