package com.ex2.jobs.common.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "address")
data class AddressEntity(

    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "landmark")
    val landmark: String? = null,

    @Column(name = "addressLine1")
    val addressLine1: String,

    @Column(name = "addressLine2")
    val addressLine2: String,

    @Column(name = "city")
    val city: String,

    @Column(name = "zipCode")
    val zipCode: String,

    @Column(name = "state")
    val state: String,

    @Column(name = "country")
    val country: String,

    @Column(name = "phone")
    val phone: String? = null
)


enum class AddressType {
    MAIN,
    BRANCH,
}
