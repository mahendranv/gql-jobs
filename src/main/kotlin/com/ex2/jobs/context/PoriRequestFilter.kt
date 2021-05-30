package com.ex2.jobs.context

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Request filter responsible for creating request attributes.
 */
@Component
class PoriRequestFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var requestUtils: PoriRequestUtils

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        requestUtils.saveSessionData(request)

        val session = requestUtils.getSessionData()
        println("PoriRequestFilter.doFilterInternal $session")

        // Resume request handling
        filterChain.doFilter(request, response)
    }
}