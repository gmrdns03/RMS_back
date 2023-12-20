package com.project.LimeRMS.mapper;

import com.project.LimeRMS.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> findByUserEmail(String userEmail);

    User findByUserId(@Param("userId") String userId);
    void updatePasswordByUserEmail(@Param("userEmail") String userEmail, @Param("password") String password);

    void signup(UserSignupDto userSignupDto);
    void updateProfileImgByUserId(@Param("userId") String userId, @Param("profileImg") String profileImg);

    List<User> findAllUser();

    Integer findAuthIdByUserId(Integer userId);

    void addUser(@Param("userEmail")String userEmail, @Param("userNm")String userNm, @Param("password")String password, @Param("phoneNumber")String phoneNumber);

}
