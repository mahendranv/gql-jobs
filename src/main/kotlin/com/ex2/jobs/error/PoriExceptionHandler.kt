package com.ex2.jobs.error

import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler
import com.netflix.graphql.types.errors.TypedGraphQLError
import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters
import graphql.execution.DataFetcherExceptionHandlerResult
import org.springframework.stereotype.Component

@Component
class PoriExceptionHandler : DataFetcherExceptionHandler {

    private val defaultHandler = DefaultDataFetcherExceptionHandler()

    override fun onException(handlerParameters: DataFetcherExceptionHandlerParameters?)
            : DataFetcherExceptionHandlerResult {
        val exception = handlerParameters?.exception
        return if (exception is PoriException) {
            val error = TypedGraphQLError.newBuilder()
                .message(exception.errorMessage)
                .build()
            DataFetcherExceptionHandlerResult.newResult()
                .error(error)
                .build()
        } else {
            defaultHandler.onException(handlerParameters)
        }
    }
}