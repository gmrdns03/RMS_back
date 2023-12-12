package com.project.LimeRMS.mapper;

import com.project.LimeRMS.entity.Authentication;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthenticationMapper {

    void insertAuthenticationTest(Authentication authentication);

    Authentication findByRegUserId(String regUserId);
}