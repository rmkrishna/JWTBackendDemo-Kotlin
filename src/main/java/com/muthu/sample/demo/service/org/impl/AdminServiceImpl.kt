package com.muthu.sample.demo.service.org.impl

import com.muthu.sample.demo.data.db.AdminDao
import com.muthu.sample.demo.data.model.Admin
import com.muthu.sample.demo.service.org.AdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Repository

@Repository
class AdminServiceImpl : AdminService {
    @Autowired
    lateinit var adminDao: AdminDao

    /**
     * Generic class to update the admin details based on User name
     */
    override fun editAdmin(admin: Admin): Int {
        // If user not exists return -1
        if (!adminDao.isUserExists(admin.userName)) {
            return -1
        }

        val newAdmin = admin.copy(role = 2)

        val rows = adminDao.updateWithoutPassword(newAdmin)

        return if (rows) 1 else 0
    }

    override fun doLogin(admin: Admin): Int? {
        val localAdmin = adminDao.getAdminWithPassword(admin.userName)

        localAdmin?.let {
            val encoder = BCryptPasswordEncoder()

            val isMatching = encoder.matches(admin.password, localAdmin.password)

            if (isMatching) {
                return 0
            } else {
                return -2
            }
        }

        return -1
    }

    override fun getAllAdmin(): List<Admin>? {
        return adminDao.getAllAdmin()
    }

    override fun loadUserByUsername(username: String?): UserDetails {

        username?.let {
            val localAdmin = adminDao.getAdminWithPassword(username)

            localAdmin?.let { admin ->
                val authorities = ArrayList<GrantedAuthority>()

                authorities.add(SimpleGrantedAuthority(admin.role.toString()))

                return org.springframework.security.core.userdetails.User(admin.userName, admin.password, authorities)
            } ?: throw UsernameNotFoundException(String.format("The username %s doesn't exist", username))
        } ?: throw UsernameNotFoundException(String.format("The username should not be bull"))
    }

    override fun getAdmin(userName: String) = adminDao.findAdmin(userName)

    override fun addAdmin(admin: Admin): Int {

        val localAdmin = adminDao.findAdmin(admin.userName)

        localAdmin?.let {
            return -1
        }

        val newAdmin = admin.copy(password = BCryptPasswordEncoder().encode(admin.password!!), role = 2)

        val rows = adminDao.add(newAdmin)

        return if (rows) 1 else 0
    }
}