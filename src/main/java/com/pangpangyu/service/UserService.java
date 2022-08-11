package com.pangpangyu.service;

import com.pangpangyu.po.User;

public interface UserService {

    User checkUser(String username,String password);

    User findByUsername(String username);
}
