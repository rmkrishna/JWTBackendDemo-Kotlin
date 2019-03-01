package com.muthu.sample.demo.service.org

import com.muthu.sample.demo.data.model.Admin
import org.springframework.security.core.userdetails.UserDetailsService

interface AdminService : UserDetailsService {
    fun addAdmin(admin: Admin): Int

    fun editAdmin(admin: Admin): Int

    fun doLogin(admin: Admin): Int?

    /**
     * Should call only if admin already exists
     */
    fun getAdmin(userName: String): Admin?

    fun getAllAdmin(): List<Admin>?
}