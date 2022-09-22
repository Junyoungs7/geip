package com.geip.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geip.dto.LeagueEntryDTO;
import com.geip.dto.MainSummonerDTO;
import com.geip.dto.SummonerDTO;
import com.geip.dto.TeamBuildingRiotApiDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                if(leagueEntryDTOS.get(0).getQueueType() == "RANKED_SOLO_5x5")
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



}
