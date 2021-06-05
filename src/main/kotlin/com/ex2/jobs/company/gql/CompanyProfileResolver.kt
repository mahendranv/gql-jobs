package com.ex2.jobs.company.gql

import com.ex2.jobs.company.model.CompanyProfileEntity
import com.ex2.jobs.company.service.CompanyProfileService
import com.ex2.jobs.context.PoriRequestUtils
import com.ex2.jobs.gen.DgsConstants
import com.ex2.jobs.gen.types.CompanyProfile
import com.ex2.jobs.gen.types.CompanyProfileInput
import com.ex2.jobs.security.EmployerOnly
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class CompanyProfileResolver {

    @Autowired
    private lateinit var companyProfileService: CompanyProfileService

    @Autowired
    private lateinit var requestUtils: PoriRequestUtils

    @EmployerOnly
    @DgsMutation(field = DgsConstants.MUTATION.SaveCompanyProfile)
    fun saveProfile(@InputArgument("data") data: CompanyProfileInput): CompanyProfile {
        val id = requestUtils.employerIdOrThrow().toString()
        return companyProfileService.save(data.toEntity(id)).toGraph()
    }

    @DgsQuery(field = DgsConstants.QUERY.GetCompanyProfile)
    fun getProfile(id: String): CompanyProfile? {
        return companyProfileService.get(id)?.toGraph()
    }
}

private fun CompanyProfileInput.toEntity(id: String): CompanyProfileEntity {
    return CompanyProfileEntity(
        id = id,
        title = title,
        tagLine = tagLine,
        imageUrl = imageUrl,
        size = size,
        website = website
    )
}

private fun CompanyProfileEntity.toGraph(): CompanyProfile {
    return CompanyProfile(
        id = id!!,
        title = title,
        tagLine = tagLine,
        imageUrl = imageUrl,
        size = size,
        website = website,
        address = null,
        contact = null
    )
}
