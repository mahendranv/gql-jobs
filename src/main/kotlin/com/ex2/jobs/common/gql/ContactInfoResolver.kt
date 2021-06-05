package com.ex2.jobs.common.gql

import com.ex2.jobs.common.model.ContactInfoEntity
import com.ex2.jobs.common.service.ContactInfoService
import com.ex2.jobs.context.PoriRequestUtils
import com.ex2.jobs.gen.DgsConstants
import com.ex2.jobs.gen.types.ApplicantProfile
import com.ex2.jobs.gen.types.CompanyProfile
import com.ex2.jobs.gen.types.ContactInfo
import com.ex2.jobs.gen.types.ContactInfoInput
import com.netflix.graphql.dgs.*
import graphql.schema.DataFetchingEnvironment
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
        return service.saveContact(contact.toEntity(requstUtils.memberIdOrThrow().toString()))
            .toGraph()
    }


    // Nesting
    @DgsData(
        parentType = DgsConstants.COMPANYPROFILE.TYPE_NAME,
        field = DgsConstants.COMPANYPROFILE.Contact
    )
    fun getCompanyContactInfo(dfe: DataFetchingEnvironment): ContactInfo? {
        val id = dfe.getSource<CompanyProfile>().id
        return service.getContactInfo(id)?.toGraph()
    }

    @DgsData(
        parentType = DgsConstants.APPLICANTPROFILE.TYPE_NAME,
        field = DgsConstants.COMPANYPROFILE.Contact
    )
    fun getApplicantContactInfo(dfe: DataFetchingEnvironment): ContactInfo? {
        val id = dfe.getSource<ApplicantProfile>().id
        return service.getContactInfo(id)?.toGraph()
    }
}

private fun ContactInfoInput.toEntity(id: String): ContactInfoEntity =
    ContactInfoEntity(id = id, phone = phone, email = email)

private fun ContactInfoEntity.toGraph() =
    ContactInfo(phone = phone!!, altPhone = listOf(), email = email!!)
