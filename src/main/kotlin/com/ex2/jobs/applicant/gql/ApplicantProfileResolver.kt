package com.ex2.jobs.applicant.gql

import com.ex2.jobs.gen.DgsConstants
import com.ex2.jobs.gen.types.ApplicantProfile
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsQuery
import graphql.schema.DataFetchingEnvironment

@DgsComponent
class ApplicantProfileResolver {

    @DgsQuery(field = DgsConstants.QUERY.ViewApplicantProfile)
    fun viewApplicantProfile(id: String): ApplicantProfile? {
        return ApplicantProfile(id = id)
    }


}