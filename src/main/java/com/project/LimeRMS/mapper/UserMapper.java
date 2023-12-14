package com.project.LimeRMS.mapper;

import com.project.LimeRMS.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> findByUserEmail(String userEmail);
    void updatePasswordByUserEmail(@Param("userEmail") String userEmail, @Param("password") String password);
}
