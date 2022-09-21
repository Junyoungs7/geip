package com.geip.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class MainSummonerDTO {

    private String name;
    private long summonerLevel;
    private String profileImg;

    @Builder
    public MainSummonerDTO(String name, long summonerLevel, String profileImg){
        this.name = name;
        this.summonerLevel = summonerLevel;
        this.profileImg = profileImg;
    }
}
