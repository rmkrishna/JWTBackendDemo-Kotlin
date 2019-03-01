package com.muthu.sample.demo.jwt

import com.muthu.sample.demo.service.org.AdminService
import io.jsonwebtoken.Claims
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

const val filterHeader = "Authorization"
const val tokenPrefix = "Bearer "

/**
 * Helper class the Filer the authentication request
 */
class AuthFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    lateinit var adminService: AdminService

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val header = request.getHeader(filterHeader)

        header?.let {
            // Just remove the prefix and check the token is valid or not
            if (it.startsWith(tokenPrefix)) {
                println("it $it")
                val authToken: String = it.removePrefix(tokenPrefix)

                var claims: Claims? = jwtTokenUtil.getClaimsFromToken(authToken)

                claims?.let {
                    println("it.subject ${it.subject}")
                    it.subject?.let { userName ->
                        if (SecurityContextHolder.getContext().authentication == null) {
                            val userDetails: UserDetails = adminService.loadUserByUsername(userName)

                            println("userDetails.subject ${userDetails.username}")

                            if (jwtTokenUtil.isValidToken(claims, userDetails)) {
                                val authentication = UsernamePasswordAuthenticationToken(
                                        userDetails, null, Arrays.asList(SimpleGrantedAuthority(it.audience)))

                                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                                SecurityContextHolder.getContext().authentication = authentication
                            } else {
                                println("userDetails.subject is not valid")
                            }
                        }
                    }
                }
            }
        }

        println("End")

        filterChain.doFilter(request, response)
    }
}