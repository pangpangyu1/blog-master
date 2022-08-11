package com.pangpangyu;

import com.pangpangyu.dao.BlogRepository;
import com.pangpangyu.util.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private RedisUtils redisUtils;
    @Test
    void test() {

    }

}
