package com.geip.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendTeamBuildingDto {
    private String name;
    private String tier;
    private String rank;
    private int wins;
    private int losses;
    private int point;

    @Builder
    public RecommendTeamBuildingDto(String name, String tier, String rank, int wins, int losses, int point){
        this.name = name;
        this.tier = tier;
        this.rank = rank;
        this.wins = wins;
        this.losses = losses;
        this.point = point;
    }
}
