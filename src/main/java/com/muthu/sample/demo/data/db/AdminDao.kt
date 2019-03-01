package com.muthu.sample.demo.data.db

import com.muthu.sample.demo.data.model.Admin

interface AdminDao {
    fun add(admin: Admin): Boolean

    fun update(admin: Admin): Boolean

    fun updateWithoutPassword(admin: Admin): Boolean

    fun findAdmin(userName: String): Admin?

    fun getAdminWithPassword(userName: String): Admin?

    fun getAllAdmin(): List<Admin>?

    fun isUserExists(userName: String): Boolean
}
