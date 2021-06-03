package com.ex2.jobs.common.service

import com.ex2.jobs.common.model.AddressEntity
import com.ex2.jobs.common.model.AddressRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AddressService {

    @Autowired
    lateinit var repo: AddressRepo

    fun saveAddress(addressEntity: AddressEntity): AddressEntity = repo.save(addressEntity)

    fun getAddress(id: String) = repo.findByIdOrNull(id)
}