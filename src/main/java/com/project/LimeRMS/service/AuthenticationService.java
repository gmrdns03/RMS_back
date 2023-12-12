package com.project.LimeRMS.service;

import com.project.LimeRMS.entity.Authentication;
import com.project.LimeRMS.mapper.AuthenticationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationMapper authenticationMapper;

    public void insertAuthentication(Authentication authentication) throws Exception{
        authenticationMapper.insertAuthenticationTest(authentication);
    }

}
