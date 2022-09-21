package com.geip.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SuccessTeamBuildingDto {
    private List<TeamDto> teamDtos;
}
