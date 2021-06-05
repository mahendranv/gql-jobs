package com.ex2.jobs.company.model

import org.springframework.data.jpa.repository.JpaRepository

interface CompanyProfileRepo : JpaRepository<CompanyProfileEntity, String>