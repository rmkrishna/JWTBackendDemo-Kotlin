package com.muthu.sample.demo.jwt

import com.muthu.sample.demo.service.org.AdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class JwtSecurityConfig @Autowired
constructor(private val adminService: AdminService) : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var jwtAuthEntryPoint: JwtAuthEntryPoint

    @Throws(Exception::class)
    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Autowired
    @Throws(Exception::class)
    fun globalUserDetails(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(adminService).passwordEncoder(encoder())
    }

    fun encoder() = BCryptPasswordEncoder()

    @Throws(Exception::class)
    @Bean
    fun authenticationTokenFilterBean(): AuthFilter {
        return AuthFilter()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(adminService)
                .passwordEncoder(BCryptPasswordEncoder())
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/api/admin/signup", "/api/admin/signin").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter::class.java)

    }
}