package com.geip.service;

import com.geip.domain.user.User;
import com.geip.domain.user.UserRepository;
import com.geip.dto.MainUserInfoDto;
import com.geip.dto.TeamBuildingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Transactional
    public List<TeamBuildingDto> multiSearchNicknameDB(String gameNicknames){
        List<String> gameNickname = Arrays.asList(gameNicknames.split(","));

        List<TeamBuildingDto> infoDtoList = new ArrayList<>();
        for(String gameNick : gameNickname){
            User user = userRepository.findByGameNickname(gameNick);
            TeamBuildingDto teamBuildingDto = new TeamBuildingDto(user);
            infoDtoList.add(teamBuildingDto);
        }
        return infoDtoList;
    }


}
