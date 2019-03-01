package com.muthu.sample.demo.data.db.mapper

import com.muthu.sample.demo.data.model.Admin
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet


class AdminMapper(private val needPassword: Boolean = false) : RowMapper<Admin> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Admin? {
        return Admin(rs.getLong("id"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("userName"),
                if (needPassword) rs.getString("password") else "",
                rs.getInt("role"))
    }
}

