package com.project.LimeRMS.mapper;

import com.project.LimeRMS.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> findByUserEmail(String userEmail);
}
