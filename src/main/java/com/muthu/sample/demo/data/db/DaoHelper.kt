package com.muthu.sample.demo.data.db

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.RowMapperResultSetExtractor

fun <T> JdbcTemplate.getSingleItem(sql: String, args: Array<String>?, rowMapper: RowMapper<T>): T? {

    val results = query(sql, args, RowMapperResultSetExtractor<T>(rowMapper))

    results?.let {
        if (it.size > 0) {
            return it[0]
        }
    }
    return null
}

fun <T> JdbcTemplate.getItems(sql: String, args: Array<String>? = null,
                              rowMapper: RowMapper<T>): List<T>? = query(sql, args, RowMapperResultSetExtractor<T>(rowMapper))

fun JdbcTemplate.getCount(sql: String, args: Array<String>? = null): Int = queryForObject(sql, args, Int::class.java)