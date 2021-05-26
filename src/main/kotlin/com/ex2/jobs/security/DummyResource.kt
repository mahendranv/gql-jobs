package com.ex2.jobs.security

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import java.util.*

/**
 * This is a dummy class to try authorization
 */
@DgsComponent
class DummyResource {

    @DgsQuery
    fun resourceApplicant() = UUID.randomUUID().toString()

    @DgsQuery
    fun resourceAdmin() = UUID.randomUUID().toString()

    @DgsQuery
    fun resourceEmployer() = UUID.randomUUID().toString()

}