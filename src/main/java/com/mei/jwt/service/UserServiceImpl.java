package com.mei.jwt.service;

import com.mei.jwt.entity.User;
import com.mei.jwt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        User loginDB = userMapper.login(user);
        if (loginDB !=null){
            return loginDB;
        }
        throw new RuntimeException("login fall");
    }
}
