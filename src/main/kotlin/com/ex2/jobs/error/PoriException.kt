package com.ex2.jobs.error

import graphql.GraphQLException

class PoriException(
    val errorMessage: String,
    val code: Int = 1
) : GraphQLException(
    errorMessage
)