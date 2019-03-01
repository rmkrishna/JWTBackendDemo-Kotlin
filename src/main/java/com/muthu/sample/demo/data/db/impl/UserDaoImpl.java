package com.muthu.sample.demo.data.db.impl;

import com.muthu.sample.demo.data.db.UserDao;
import com.muthu.sample.demo.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public void add(User user) {
        jdbcTemplate.update("INSERT INTO user (name, password) values(?,?)", "Muthu", "12345");
    }
}
