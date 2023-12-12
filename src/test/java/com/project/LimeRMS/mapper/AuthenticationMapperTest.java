package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.AuthenticationDto;
import com.project.LimeRMS.service.AuthenticationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthenticationMapperTest {

    private AuthenticationDto authenticationDto;
    private AuthenticationMapper authenticationMapper;

    @Test
    @DisplayName("MyBatis 동작 테스트 코드")
    public void insertAuthenticationDtoTest() throws Exception {
        // Given
        String authNm = "TestAuth";
        String authDesc = "Test Description";
        Integer priority = 4;
        String regUserId = "snowfluppy";
        String modfUserId = "snowfluppy";
        AuthenticationDto authenticationDto = new AuthenticationDto(authNm, authDesc, priority, regUserId, modfUserId);

        // When
        authenticationMapper.insertAuthenticationDto(authenticationDto);

        // Then

    }
}


