package com.ex2.jobs.company.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "company_profile")
data class CompanyProfileEntity(

    @Id
    @Column(name = "id")
    val id: String? = null,

    @Column(name = "title")
    val title: String,

    @Column(name = "tag_line")
    val tagLine: String,

    @Column(name = "image_url")
    val imageUrl: String,

    @Column(name = "size")
    val size: Int,

    @Column(name = "website")
    val website: String
)