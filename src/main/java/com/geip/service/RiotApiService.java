package com.geip.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geip.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RiotApiService {

    @Value("${RiotApiKey}")
    private String apiKey;

    @Transactional
    public MainSummonerDTO SearchSummonerName(String name) throws JsonProcessingException {
        String summonerURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + name + "?api_key=" + apiKey;
        String summonerResult = WebClient.create(summonerURL)
                .get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();
        SummonerDTO summonerDTO = mapper.readValue(summonerResult, SummonerDTO.class);

        String iconURL = "http://ddragon.leagueoflegends.com/cdn/12.18.1/img/profileicon/"+summonerDTO.getProfileIconId()+".png";

        String leagueURL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerDTO.getId() + "?api_key=" + apiKey;

        String leagueResult = WebClient.create(leagueURL)
                .get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        List<LeagueEntryDTO> leagueEntryDTOS = Arrays.asList(mapper.readValue(leagueResult, LeagueEntryDTO[].class));
        LeagueEntryDTO leagueEntryDTO = new LeagueEntryDTO();

        if(leagueEntryDTOS.size() == 1){
            leagueEntryDTO = leagueEntryDTOS.get(0);
        } else if(leagueEntryDTOS.size() == 2 ){
            if(leagueEntryDTOS.get(0).getQueueType() == "RANKED_SOLO_5x5")
                leagueEntryDTO = leagueEntryDTOS.get(0);
            else
                leagueEntryDTO = leagueEntryDTOS.get(1);
        }

        return MainSummonerDTO.builder()
                .name(summonerDTO.getName())
                .summonerLevel(summonerDTO.getSummonerLevel())
                .profileImg(iconURL)
                .leaguePoints(leagueEntryDTO.getLeaguePoints())
                .losses(leagueEntryDTO.getLosses())
                .rank(leagueEntryDTO.getRank())
                .tier(leagueEntryDTO.getTier())
                .wins(leagueEntryDTO.getWins())
                .build();
    }

    @Transactional
    public List<TeamBuildingRiotApiDTO> teamBuildingSearchSummoner(String names) throws JsonProcessingException {
        List<String> gameNickname = Arrays.asList(names.split(","));

        List<TeamBuildingRiotApiDTO> teamBuildingRiotApiDTOS = new ArrayList<>();
        for(String name : gameNickname){
            String summonerURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + name + "?api_key=" + apiKey;
            String summonerResult = WebClient.create(summonerURL)
                    .get()
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper mapper = new ObjectMapper();
            SummonerDTO summonerDTO = mapper.readValue(summonerResult, SummonerDTO.class);

            String leagueURL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerDTO.getId() + "?api_key=" + apiKey;

            String leagueResult = WebClient.create(leagueURL)
                    .get()
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();


            List<LeagueEntryDTO> leagueEntryDTOS = Arrays.asList(mapper.readValue(leagueResult, LeagueEntryDTO[].class));
            LeagueEntryDTO leagueEntryDTO = new LeagueEntryDTO();

            if(leagueEntryDTOS.size() == 1){
                leagueEntryDTO = leagueEntryDTOS.get(0);
            } else if(leagueEntryDTOS.size() == 2 ){
                if(leagueEntryDTOS.get(0).getQueueType().equals("RANKED_SOLO_5x5"))
                    leagueEntryDTO = leagueEntryDTOS.get(0);
                else
                    leagueEntryDTO = leagueEntryDTOS.get(1);
            }

            TeamBuildingRiotApiDTO teamBuildingRiotApiDTO = TeamBuildingRiotApiDTO.builder()
                    .leagueEntryDTO(leagueEntryDTO)
                    .build();
            teamBuildingRiotApiDTOS.add(teamBuildingRiotApiDTO);
        }
        return teamBuildingRiotApiDTOS;

    }

    @Transactional
    public RecommendTBAndControllerDTO recommendTeamBuilding(String names) throws JsonProcessingException {
        List<TeamBuildingRiotApiDTO> searchSummoners = teamBuildingSearchSummoner(names);
        List<RecommendTeamBuildingDto> recommendTeamBuildingDtoList = new ArrayList<>();
        List<RecommendTeamBuildingDto> redTeam = new ArrayList<>();
        List<RecommendTeamBuildingDto> blueTeam = new ArrayList<>();

        for(TeamBuildingRiotApiDTO teamBuildingRiotApiDTO : searchSummoners){
            int point = 0;
            switch (teamBuildingRiotApiDTO.getTier()){
                case "CHALLENGER":
                    point = 9;
                    break;
                case "GRANDMASTER":
                    point = 8;
                    break;
                case "MASTER":
                    point = 7;
                    break;
                case "DIAMOND":
                    point = 6;
                    break;
                case "PLATINUM":
                    point = 5;
                    break;
                case "GOLD":
                    point = 4;
                    break;
                case "SILVER":
                    point = 3;
                    break;
                case "BRONZE":
                    point = 2;
                    break;
                case "IRON":
                    point = 1;
                    break;
            }
            RecommendTeamBuildingDto recommendTeamBuildingDto = RecommendTeamBuildingDto.builder()
                    .name(teamBuildingRiotApiDTO.getName())
                    .rank(teamBuildingRiotApiDTO.getRank())
                    .tier(teamBuildingRiotApiDTO.getTier())
                    .wins(teamBuildingRiotApiDTO.getWins())
                    .losses(teamBuildingRiotApiDTO.getLosses())
                    .point(point)
                    .build();
            recommendTeamBuildingDtoList.add(recommendTeamBuildingDto);
        }
        recommendTeamBuildingDtoList = recommendTeamBuildingDtoList.stream().sorted(Comparator.comparing(RecommendTeamBuildingDto::getPoint).reversed()).collect(Collectors.toList());

        for(int i = 0; i < recommendTeamBuildingDtoList.size(); i++){
            if(i%2 == 0){
                redTeam.add(recommendTeamBuildingDtoList.get(i));
            }else{
                blueTeam.add(recommendTeamBuildingDtoList.get(i));
            }

        }

        for(int i = 1; i < redTeam.size(); i++){
            if(redTeam.get(i-1).getPoint() > blueTeam.get(i-1).getPoint()){
                if(redTeam.get(i).getPoint() > blueTeam.get(i).getPoint()){
                    RecommendTeamBuildingDto temp1 = redTeam.get(i);
                    RecommendTeamBuildingDto temp2 = blueTeam.get(i);
                    redTeam.set(i, temp2);
                    blueTeam.set(i, temp1);
                }
            }else if(redTeam.get(i-1).getPoint() < blueTeam.get(i-1).getPoint()){
                if(redTeam.get(i).getPoint() < blueTeam.get(i).getPoint()){
                    RecommendTeamBuildingDto temp1 = redTeam.get(i);
                    RecommendTeamBuildingDto temp2 = blueTeam.get(i);
                    redTeam.set(i, temp2);
                    blueTeam.set(i, temp1);
                }
            }
        }
        RecommendTBAndControllerDTO recommendTBAndControllerDTO = new RecommendTBAndControllerDTO();
        recommendTBAndControllerDTO.setRedTeam(redTeam);
        recommendTBAndControllerDTO.setBlueTeam(blueTeam);
        return recommendTBAndControllerDTO;

    }



}
