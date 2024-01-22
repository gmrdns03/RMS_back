package com.project.LimeRMS.Admin.mapper;

import com.project.LimeRMS.Admin.dto.AuthListDto;
import com.project.LimeRMS.User.dto.UserAuthPriorityDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthenticationMapper {

    List<AuthListDto> findAllAuth();
    UserAuthPriorityDto findOneAuthByUserId(String userId);
}