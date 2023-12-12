package com.project.LimeRMS.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.project.LimeRMS.entity.Authentication;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
public class AuthenticationMapperTest {

    @Autowired
    AuthenticationMapper authenticationMapper;

    //Test 코드(나중에 연관 코드 삭제 필요)
    @Test
    @Transactional
    @DisplayName("MyBatis 동작 테스트 코드")
    void insertAuthenticationTest() {

        // Given
        Authentication authentication = new Authentication();
        authentication.setAuthNm("TestAuth");
        authentication.setAuthDesc("Test Description");
        authentication.setPriority(4);
        authentication.setRegUserId("snowfluppy");
        authentication.setModfUserId("snowfluppy");

        // Where
        authenticationMapper.insertAuthenticationTest(authentication);

        // Then
        Authentication insertedAuthentication = authenticationMapper.findByRegUserId("snowfluppy");
        assertEquals(authentication.getAuthNm(), insertedAuthentication.getAuthNm());
        assertEquals(authentication.getAuthDesc(), insertedAuthentication.getAuthDesc());
        assertEquals(authentication.getPriority(), insertedAuthentication.getPriority());
        assertEquals(authentication.getRegUserId(), insertedAuthentication.getRegUserId());
        assertEquals(authentication.getModfUserId(), insertedAuthentication.getModfUserId());
    }
}


