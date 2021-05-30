package com.ex2.jobs.context

import com.ex2.jobs.security.UserRoles

data class PoriSession(

    var token: String?,

    var memberId: Long?,

    val role: UserRoles

) {

    val hasActiveSession = token != null

}
