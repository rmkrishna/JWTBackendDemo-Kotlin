package com.muthu.sample.demo.data.db.impl

import com.muthu.sample.demo.data.db.AdminDao
import com.muthu.sample.demo.data.db.getCount
import com.muthu.sample.demo.data.db.getItems
import com.muthu.sample.demo.data.db.getSingleItem
import com.muthu.sample.demo.data.db.mapper.AdminMapper
import com.muthu.sample.demo.data.model.Admin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository


const val adminTableName = "org_user"

const val getAllAdmin = "SELECT * FROM $adminTableName"
const val getCountForUser = "SELECT COUNT(*) FROM $adminTableName WHERE userName = ?"
const val getAdminByUserName = "SELECT * FROM $adminTableName WHERE userName = ?"
const val addAdminQuery = "INSERT INTO $adminTableName (firstName, lastName, userName, password, role) values(?,?,?,?,?)"
const val updateAdminQuery = "UPDATE $adminTableName SET firstName = ?, lastName = ?, password = ?, role = ? WHERE userName = ?"
const val updateWithoutPasswordAdminQuery = "UPDATE $adminTableName SET firstName = ?, lastName = ?, role = ? WHERE userName = ?"

@Repository
class AdminDaoImpl : AdminDao {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    override fun getAllAdmin(): List<Admin>? {
        return jdbcTemplate.getItems(getAllAdmin, rowMapper = AdminMapper())
    }

    override fun add(admin: Admin): Boolean {
        return jdbcTemplate.update(addAdminQuery, admin.firstName, admin.lastName, admin.userName, admin.password, admin.role) > 0
    }

    override fun updateWithoutPassword(admin: Admin): Boolean {
        return jdbcTemplate.update(updateWithoutPasswordAdminQuery, admin.firstName, admin.lastName, admin.role, admin.userName) > 0
    }

    override fun update(admin: Admin): Boolean {
        return jdbcTemplate.update(updateAdminQuery, admin.firstName, admin.lastName, admin.password, admin.role, admin.userName) > 0
    }

    override fun findAdmin(userName: String): Admin? {
        return jdbcTemplate.getSingleItem(getAdminByUserName, arrayOf(userName), AdminMapper())
    }

    override fun getAdminWithPassword(userName: String): Admin? {
        return jdbcTemplate.getSingleItem(getAdminByUserName, arrayOf(userName), AdminMapper(true))
    }

    override fun isUserExists(userName: String): Boolean {
        return jdbcTemplate.getCount(getCountForUser, arrayOf(userName)) > 0
    }
}
