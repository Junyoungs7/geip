package com.geip.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecommendTBAndControllerDTO {

    private List<RecommendTeamBuildingDto> redTeam;
    private List<RecommendTeamBuildingDto> blueTeam;

}
