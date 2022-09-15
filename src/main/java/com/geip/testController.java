package com.geip;

import com.geip.config.auth.LoginUser;
import com.geip.config.auth.dto.SessionUser;
import com.geip.dto.MainUserInfoDto;
import com.geip.dto.UpdateGameNicknameDto;
import com.geip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class testController {

    private final UserService userService;

    @GetMapping("/")
    public String Main(Model model, @LoginUser SessionUser sessionUser){
        MainUserInfoDto mainUserInfoDto = userService.mainUserInfoDto(sessionUser.getEmail());
        model.addAttribute("userInfo", mainUserInfoDto);
        return "index";
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
}
