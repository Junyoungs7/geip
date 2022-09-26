package com.geip.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geip.config.auth.LoginUser;
import com.geip.config.auth.dto.SessionUser;
import com.geip.dto.*;
import com.geip.service.RiotApiService;
import com.geip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final RiotApiService riotApiService;

    @GetMapping("/")
    public String Main(Model model, @RequestParam(required = false, name = "SummonerName") String searchName, @LoginUser SessionUser sessionUser) throws JsonProcessingException {
        MainUserInfoDto mainUserInfoDto = userService.mainUserInfoDto(sessionUser.getEmail());
        if(searchName == null)
        {
            model.addAttribute("userInfo", mainUserInfoDto);
            model.addAttribute("searchSummoner", new SummonerSearchDTO());
            return "index";
        }
        else {
            MainSummonerDTO mainSummonerDTO = riotApiService.SearchSummonerName(searchName);
            model.addAttribute("resultSearch", mainSummonerDTO);
            model.addAttribute("userInfo", mainUserInfoDto);
            model.addAttribute("searchSummoner", new SummonerSearchDTO());
            return "index";
        }

    }


    @GetMapping("/saveorupdate")
    public String gameNicknameSaveOrUpdate(Model model, UpdateGameNicknameDto updateGameNicknameDto, @LoginUser SessionUser sessionUser){
        MainUserInfoDto mainUserInfoDto = userService.mainUserInfoDto(sessionUser.getEmail());
        model.addAttribute("userInfo", mainUserInfoDto);
        model.addAttribute("Nickname", updateGameNicknameDto);
        return "postNickname";
    }

    @PostMapping("/saveorupdate")
    public String postNickname(@ModelAttribute("updateGameNicknameDto") UpdateGameNicknameDto updateGameNicknameDto, @LoginUser SessionUser sessionUser){
        userService.updateNickname(sessionUser.getEmail(), updateGameNicknameDto.getGameNickname());

        return "redirect:/";
    }

    @GetMapping("/loginform")
    public String Login(){
        return "signin";
    }

    @GetMapping("/teamBuilding")
    public String teamBuilding(MultiSearchDto multiSearchDto, Model model, @LoginUser SessionUser sessionUser){
        MainUserInfoDto mainUserInfoDto = userService.mainUserInfoDto(sessionUser.getEmail());
        model.addAttribute("userInfo", mainUserInfoDto);
        model.addAttribute("multiSearchDto", multiSearchDto);
        return "teamBuilding";
    }

    @PostMapping("/teamBuilding")
    public String multiSearch(@ModelAttribute("multiSearchDto") MultiSearchDto multiSearchDto, Model model, @LoginUser SessionUser sessionUser) throws JsonProcessingException {
        String userNicknames = multiSearchDto.getGameNicknames();
        MainUserInfoDto mainUserInfoDto = userService.mainUserInfoDto(sessionUser.getEmail());
        List<TeamBuildingRiotApiDTO> teamBuildingRiotApiDTOS = riotApiService.teamBuildingSearchSummoner(userNicknames);
        model.addAttribute("userInfo", mainUserInfoDto);
        model.addAttribute("multiSearchList", teamBuildingRiotApiDTOS);
        return "teamBuilding";
    }


    @GetMapping("/test")
    public String test() throws JsonProcessingException {
        String names = "grjun, hideonbush, 엉덩국 갱승제로, 미션충 권상윤, 난화내, 아헤가오더블피스, 흐예나, 흐예나팬";
        riotApiService.recommendTeamBuilding(names);
        return null;
    }

}
