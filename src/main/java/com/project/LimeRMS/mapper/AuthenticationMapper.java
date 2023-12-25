package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.AuthListDto;
import com.project.LimeRMS.dto.UserAuthPriorityDto;
import com.project.LimeRMS.entity.Authentication;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthenticationMapper {

    List<AuthListDto> findAllAuth();
    UserAuthPriorityDto findOneAuthByUserId(String userId);
}