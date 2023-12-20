package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.UserInfoDto;
import com.project.LimeRMS.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "AdminController", description = "AdminController API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public List<UserInfoDto> getUserInformation(){
        return adminService.getUserInformation();
    }

}
