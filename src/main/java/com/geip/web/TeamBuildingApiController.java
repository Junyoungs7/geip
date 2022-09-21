package com.geip.web;

import com.geip.dto.SuccessTeamBuildingDto;
import com.geip.dto.TeamDto;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TeamBuildingApiController {

    @PostMapping("/api/teambuilding")
    public List<TeamDto> teamBuild(@RequestBody List<TeamDto> teamDtos){
        System.out.println(teamDtos.toString());

        for( TeamDto teamDto : teamDtos){
            System.out.println(teamDto.toString());
        }
        return teamDtos;
    }

    @PostMapping("/api")
    public void postTest(Map<String, Object> requestData){
        requestData.forEach((key, value) -> {
            System.out.println("key : " + key);
            System.out.println("value : " + value);
        });
    }

}
