package com.ex2.jobs.common.service

import com.ex2.jobs.common.model.ContactInfoEntity
import com.ex2.jobs.common.model.ContactInfoRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ContactInfoService {

    @Autowired
    private lateinit var repo: ContactInfoRepo

    fun saveContact(entity: ContactInfoEntity) = repo.save(entity)

    fun getContactInfo(id: String) = repo.findByIdOrNull(id)
}