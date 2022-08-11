package com.pangpangyu.dao;

import com.pangpangyu.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    Blog getBlogById(Long id);

    @Query("select b from t_blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    //?1代表第一个形参query，？2就是pageable
    @Query("select b from t_blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);

    @Transactional
    @Modifying
    @Query("update t_blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    //select date_format(b.update_time,'%Y') as year from t_blog b GROUP BY year order by year
    @Query("select function('date_format',b.updateTime,'%Y') from t_blog b group by function('date_format',b.updateTime,'%Y') order by function('date_format',b.updateTime,'%Y')")
    List<String> findGroupYear();

    @Query("select b from t_blog b where function('date_format',b.updateTime,'%Y')=?1")
    List<Blog> findByYear(String year);


    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
    void cancelForeignKeyConstraint();
    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
    void enableForeignKeyConstraint();

}
