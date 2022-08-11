package com.pangpangyu.dao;

import com.pangpangyu.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);

    @Query("select t from t_tag t") //自定义查询
    List<Tag> findTop(Pageable pageable);
}
