package com.ex2.jobs.common.gql

import com.ex2.jobs.common.model.AddressEntity
import com.ex2.jobs.common.service.AddressService
import com.ex2.jobs.context.PoriRequestUtils
import com.ex2.jobs.error.ExceptionFactory
import com.ex2.jobs.gen.DgsConstants
import com.ex2.jobs.gen.types.Address
import com.ex2.jobs.gen.types.AddressInput
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class AddressResolver {

    @Autowired
    lateinit var service: AddressService

    @Autowired
    lateinit var requestUtils: PoriRequestUtils

    @DgsQuery(field = DgsConstants.QUERY.GetAddress)
    fun getAddress(): Address {
        val address = service.getAddress(id = requestUtils.memberIdOrThrow().toString())
            ?: throw ExceptionFactory.plain("Address not found")
        return address.toGraph()
    }

    @DgsMutation(field = DgsConstants.MUTATION.SaveAddress)
    fun saveAddress(address: AddressInput) =
        service
            .saveAddress(
                address.toEntity(requestUtils.memberIdOrThrow().toString())
            ).toGraph()
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