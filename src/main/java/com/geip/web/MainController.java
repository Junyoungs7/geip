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
            System.out.println("controller 1");
            model.addAttribute("userInfo", mainUserInfoDto);
            model.addAttribute("searchSummoner", new SummonerSearchDTO());
            return "index";
        }
        else {
            System.out.println("controller 2");
            MainSummonerDTO mainSummonerDTO = riotApiService.SearchSummonerName(searchName);
            System.out.println(mainSummonerDTO.toString());

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
    public String multiSearch(@ModelAttribute("multiSearchDto") MultiSearchDto multiSearchDto, Model model, @LoginUser SessionUser sessionUser){
        String userNicknames = multiSearchDto.getGameNicknames();
        List<TeamBuildingDto> teamBuildingDtos = userService.multiSearchNickname(userNicknames);
        MainUserInfoDto mainUserInfoDto = userService.mainUserInfoDto(sessionUser.getEmail());
        model.addAttribute("userInfo", mainUserInfoDto);
        model.addAttribute("multiSearchList", teamBuildingDtos);
        return "teamBuilding";
    }

}
