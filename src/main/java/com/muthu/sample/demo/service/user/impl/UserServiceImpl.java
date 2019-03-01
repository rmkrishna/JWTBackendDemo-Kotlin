package com.muthu.sample.demo.service.user.impl;

import com.muthu.sample.demo.data.db.UserDao;
import com.muthu.sample.demo.data.model.User;
import com.muthu.sample.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserServiceImpl implements UserService {

    @Autowired
    public UserDao userDao;

    @Override
    public void add(User user) {
        userDao.add(user);
    }
}
