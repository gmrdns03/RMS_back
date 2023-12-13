package com.project.LimeRMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    PasswordEncoder passwordEncoder;
    
//
//    @Test
//    void pwdEnc() {
//        String pwd = "kedric123";
//        String encodedPwd = passwordEncoder.encode(pwd); //암호화 하는부분
//        System.out.println(encodedPwd);
//    }
//
//    @Test
//    void pwdMatch(){
//        // 기존 저장해두었던 암호화된 비밀번호
//        String encodedPwd = "{bcrypt}$2a$10$xO99cg0RupsQY4PNvdPJe.neRL7JSplM8t/NQUgBRGnOM19/FbstS";
//        // 검증할 비밀번호
//        String newPwd = "kedric123";
//
//        if(passwordEncoder.matches(newPwd, originPwd){
//            System.out.println("true");
//        }else{
//            System.out.println("false");
//        }
//    }
}
