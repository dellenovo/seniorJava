package com.lifei.mybatisinjection;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabUserMapper {
    @Select("select * from tab_user where id = ${userId}")
    List<TabUser> getUserById(@Param("userId") String userId);
}
