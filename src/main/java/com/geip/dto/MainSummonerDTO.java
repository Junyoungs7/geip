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

    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;

    @Builder
    public MainSummonerDTO(String name, long summonerLevel, String profileImg, String tier, String rank, int
                           leaguePoints, int wins, int losses){
        this.name = name;
        this.summonerLevel = summonerLevel;
        this.profileImg = profileImg;
        this.tier = tier;
        this.rank = rank;
        this.leaguePoints = leaguePoints;
        this.wins = wins;
        this.losses = losses;
    }
}
