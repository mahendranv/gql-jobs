package com.ex2.jobs.common.gql

import com.ex2.jobs.common.model.AddressEntity
import com.ex2.jobs.common.service.AddressService
import com.ex2.jobs.context.PoriRequestUtils
import com.ex2.jobs.error.ExceptionFactory
import com.ex2.jobs.gen.DgsConstants
import com.ex2.jobs.gen.types.Address
import com.ex2.jobs.gen.types.AddressInput
import com.ex2.jobs.gen.types.ApplicantProfile
import com.ex2.jobs.gen.types.CompanyProfile
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class AddressResolver {

    @Autowired
    lateinit var service: AddressService

    @Autowired
    lateinit var requestUtils: PoriRequestUtils

    @DgsQuery(field = DgsConstants.QUERY.GetAddress)
    fun getAddress(): Address? {
        val address = service.getAddress(id = requestUtils.memberIdOrThrow().toString())
        return address?.toGraph()
    }

    @DgsMutation(field = DgsConstants.MUTATION.SaveAddress)
    fun saveAddress(address: AddressInput) =
        service
            .saveAddress(
                address.toEntity(requestUtils.memberIdOrThrow().toString())
            ).toGraph()


    // Nesting
    @DgsData(
        parentType = DgsConstants.APPLICANTPROFILE.TYPE_NAME,
        field = DgsConstants.APPLICANTPROFILE.Address
    )
    fun getAddress(dfe: DataFetchingEnvironment): Address? {
        val profile = dfe.getSource<ApplicantProfile>()
        return service.getAddress(profile.id)?.toGraph()
    }

    @DgsData(
        parentType = DgsConstants.COMPANYPROFILE.TYPE_NAME,
        field = DgsConstants.APPLICANTPROFILE.Address
    )
    fun getCompanyAddress(dfe: DataFetchingEnvironment): Address? {
        val profile = dfe.getSource<CompanyProfile>()
        return service.getAddress(profile.id)?.toGraph()
    }
}

private fun AddressEntity.toGraph(): Address = Address(
    addressLine1 = addressLine1,
    addressLine2 = addressLine2,
    id = id,
    city = city,
    country = country,
    landmark = landmark,
    phone = phone,
    state = state,
    zipCode = zipCode
)

fun AddressInput.toEntity(id: String) = AddressEntity(
    addressLine1 = addressLine1,
    addressLine2 = addressLine2,
    id = id,
    city = city,
    country = country,
    landmark = landmark,
    phone = phone,
    state = state,
    zipCode = zipCode
)