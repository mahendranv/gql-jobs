package com.ex2.jobs.applicant.service

import com.ex2.jobs.common.service.AddressService
import com.ex2.jobs.common.service.ContactInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ApplicantProfileService {

    @Autowired
    private lateinit var addressService: AddressService

    @Autowired
    private lateinit var contactInfoService: ContactInfoService
}