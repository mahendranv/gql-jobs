package com.ex2.jobs.context

data class PoriSession(

    var token: String?

) {

    val hasActiveSession = token != null

}
