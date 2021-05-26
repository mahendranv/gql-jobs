package com.ex2.jobs.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.*

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

//    @Bean
//    fun corsConfigurationSource(): CorsConfigurationSource? {
//        val configuration = CorsConfiguration()
//        configuration.allowedOrigins = listOf("*")
//        configuration.allowedMethods = listOf("*")
//        configuration.allowedHeaders = listOf("*")
//        configuration.allowCredentials = true
//        val source = UrlBasedCorsConfigurationSource()
//        source.registerCorsConfiguration("/**", configuration)
//        return source
//    }

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers("/graphql").permitAll()  // Enable the endpoint - resolver level checks will be added later
            .antMatchers("/graphiql").permitAll() // Enable GraphQL playground
            .anyRequest().authenticated()
    }

}