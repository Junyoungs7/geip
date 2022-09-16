package com.geip.dto;

import com.geip.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamBuildingDto {
    private String name;
    private String gameNickname;

    public TeamBuildingDto(User user){
        this.name = user.getName();
        this.gameNickname = user.getGameNickname();
    }
}
