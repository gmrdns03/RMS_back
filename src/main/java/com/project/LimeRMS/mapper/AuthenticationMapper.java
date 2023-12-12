package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.AuthenticationDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface AuthenticationMapper {

    @Insert("INSERT INTO Authentication (authNm, authDesc, priority, regUserId, modfUserId) VALUES (#{authNm}, #{authDesc}, #{priority}, #{regUserId}, #{modfUserId})")
    void insertAuthenticationDto(AuthenticationDto authenticationDto);

}