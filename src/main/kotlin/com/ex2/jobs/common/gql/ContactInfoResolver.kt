package com.ex2.jobs.common.gql

import com.ex2.jobs.common.model.ContactInfoEntity
import com.ex2.jobs.common.service.ContactInfoService
import com.ex2.jobs.context.PoriRequestUtils
import com.ex2.jobs.gen.DgsConstants
import com.ex2.jobs.gen.types.ContactInfo
import com.ex2.jobs.gen.types.ContactInfoInput
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class ContactInfoResolver {

    @Autowired
    private lateinit var service: ContactInfoService

    @Autowired
    private lateinit var requstUtils: PoriRequestUtils

    @DgsQuery(field = DgsConstants.QUERY.GetContactInfo)
    fun getContactInfo(): ContactInfo? {
        val result = service.getContactInfo(requstUtils.memberIdOrThrow().toString())
        return result?.toGraph()
    }

    @DgsMutation(field = DgsConstants.MUTATION.SaveContactInfo)
    fun saveContactInfo(
        @InputArgument("contactInfo")
        contact: ContactInfoInput
    ): ContactInfo {
        return service.saveContact(contact.toEntity(requstUtils.applicantIdOrThrow().toString()))
            .toGraph()
    }

}

private fun ContactInfoInput.toEntity(id: String): ContactInfoEntity =
    ContactInfoEntity(id = id, phone = phone, email = email)

private fun ContactInfoEntity.toGraph() =
    ContactInfo(phone = phone!!, altPhone = listOf(), email = email!!)
