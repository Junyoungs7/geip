package com.geip.service;

import com.geip.domain.user.User;
import com.geip.domain.user.UserRepository;
import com.geip.dto.MainUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void updateNickname(String email, String gameNickname){
        User user = userRepository.findUserByEmail(email);
        user.nicknameUpdate(gameNickname);
        userRepository.save(user);
    }

    @Transactional
    public MainUserInfoDto mainUserInfoDto(String email){
        User user = userRepository.findUserByEmail(email);
        return new MainUserInfoDto(user);
    }

}
