package com.project.LimeRMS.mapper;

import com.project.LimeRMS.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<User> findAllUser();

    Integer findAuthIdByUserId(Integer userId);
}
