package com.project.LimeRMS.Config.security;

import com.project.LimeRMS.entity.User;
import com.project.LimeRMS.User.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        User user = userMapper.findByUserEmail(userEmail).orElseThrow(
                () -> new UsernameNotFoundException("Invalid authentication!")
        );

        return new UserDetail(user);
    }
}
