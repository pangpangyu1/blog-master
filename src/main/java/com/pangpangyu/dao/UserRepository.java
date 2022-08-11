package com.pangpangyu.dao;

import com.pangpangyu.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {
    //JpaRepository<User,Long>  Long是主键的类型

    User findByUsernameAndPassword(String username,String password);

    @Query(value = "select *  from t_user where username = ?1",nativeQuery = true)
    User findByUsername(String username);
}
