package com.ex2.jobs.company.service

import com.ex2.jobs.company.model.CompanyProfileEntity
import com.ex2.jobs.company.model.CompanyProfileRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CompanyProfileService {

    @Autowired
    private lateinit var companyProfileRepo: CompanyProfileRepo

    fun save(e: CompanyProfileEntity): CompanyProfileEntity {
        return companyProfileRepo.save(e)
    }

    fun get(id: String): CompanyProfileEntity? {
        return companyProfileRepo.findByIdOrNull(id)
    }

}