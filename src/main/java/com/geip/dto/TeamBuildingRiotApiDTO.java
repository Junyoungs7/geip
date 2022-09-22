package com.geip.dto;

import com.geip.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TeamBuildingRiotApiDTO {
    private String name;
    private String tier;
    private String rank;
    private int wins;
    private int losses;

    @Builder
    public TeamBuildingRiotApiDTO(LeagueEntryDTO leagueEntryDTO){
        this.name = leagueEntryDTO.getSummonerName();
        this.tier = leagueEntryDTO.getTier();
        this.rank = leagueEntryDTO.getRank();
        this.wins = leagueEntryDTO.getWins();
        this.losses = leagueEntryDTO.getLosses();
    }
}
