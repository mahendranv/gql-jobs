package com.ex2.jobs.error

object ExceptionFactory {

    fun plain(message: String, code: Int = -1): Exception {
        return PoriException(
            message,
            code
        )
    }
}