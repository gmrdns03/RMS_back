package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.AuthenticationDto;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AuthenticationMapper {

    void insertAuthenticationDto(AuthenticationDto authenticationDto);
}