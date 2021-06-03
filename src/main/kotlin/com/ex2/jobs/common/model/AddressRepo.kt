package com.ex2.jobs.common.model

import com.ex2.jobs.common.model.AddressEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepo : JpaRepository<AddressEntity, String>