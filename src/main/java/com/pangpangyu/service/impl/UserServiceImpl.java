package com.pangpangyu.service.impl;

import com.pangpangyu.dao.UserRepository;
import com.pangpangyu.po.User;
import com.pangpangyu.service.UserService;
import com.pangpangyu.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
    @Override
    public User findByUsername(String username) {
        User user=userRepository.findByUsername(username);
        return user;
    }
}
