package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.AuthenticationDto;
import com.project.LimeRMS.service.AuthenticationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthenticationMapperTest {

    @Autowired
    private AuthenticationMapper authenticationMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("mybatis 동작 테스트 코드")
    public void insertAuthenticationDtoTest() throws Exception {
        // Given
        String authNm = "TestAuth";
        String authDesc = "Test Description";
        Integer priority = 4;
        String regUserId = "snowfluppy";
        String modfUserId = "snowfluppy";
        AuthenticationDto authenticationDto = new AuthenticationDto(authNm, authDesc, priority,
            regUserId, modfUserId);

        // When
        authenticationMapper.insertAuthenticationDto(authenticationDto);

        // Then


    }
}


