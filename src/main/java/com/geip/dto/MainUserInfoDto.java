package com.geip.dto;

import com.geip.domain.user.Role;
import com.geip.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainUserInfoDto {
    private String name;
    private String picture;
    private String gameNickname;

    public MainUserInfoDto(User user){
        this.name = user.getName();
        this.gameNickname = user.getGameNickname();
        this.picture = user.getPicture();
    }
}
