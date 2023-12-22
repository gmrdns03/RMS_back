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

    void updateProfileImgByUserId(@Param("userId") String userId, @Param("profileImg") String profileImg);

    List<User> findAllUser();

    void addUser(@Param("userEmail")String userEmail, @Param("userNm")String userNm, @Param("password")String password, @Param("phoneNumber")String phoneNumber, @Param("authId") Integer authId);

    void updatePwByUserId(@Param("userId")Integer userId, @Param("password")String password);

    void updateUserByUserId(@Param("userId")Integer userId, @Param("userNm")String userNm, @Param("authId")Integer authId, @Param("userStat")String userStat, @Param("phoneNumber")String phoneNumber, @Param("modfUserId")Integer modfUserId);
}
