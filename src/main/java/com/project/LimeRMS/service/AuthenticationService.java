package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.AuthenticationDto;
import com.project.LimeRMS.mapper.AuthenticationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private AuthenticationMapper authenticationMapper;

    public void insertDto(AuthenticationDto authenticationDto) throws Exception{
        authenticationMapper.insertAuthenticationDto(authenticationDto);
    }

}
