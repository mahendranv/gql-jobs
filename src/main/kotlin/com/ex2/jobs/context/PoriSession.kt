package com.ex2.jobs.context

data class PoriSession(

    var token: String?,

    var memberId: Long?

) {

    val hasActiveSession = token != null

}
