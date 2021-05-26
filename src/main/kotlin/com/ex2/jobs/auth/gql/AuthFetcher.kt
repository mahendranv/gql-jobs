package com.ex2.jobs.auth.gql

import com.ex2.jobs.adapters.AuthEntityMapper
import com.ex2.jobs.adapters.AuthInputEntityMapper
import com.ex2.jobs.auth.repo.AuthRepository
import com.ex2.jobs.gen.DgsConstants
import com.ex2.jobs.gen.types.Auth
import com.ex2.jobs.gen.types.AuthInput
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class AuthFetcher {

    @Autowired
    private lateinit var authRepository: AuthRepository

    @DgsData(
        parentType = DgsConstants.MUTATION.TYPE_NAME,
        field = DgsConstants.MUTATION.RegisterUser
    )
    fun registerUser(@InputArgument("auth") auth: AuthInput): Auth {
        val result = authRepository.save(AuthInputEntityMapper.toEntity(auth))
        return AuthEntityMapper.toGraph(result)
    }
}