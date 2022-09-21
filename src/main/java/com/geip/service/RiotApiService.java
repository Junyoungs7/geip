package com.geip.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geip.dto.MainSummonerDTO;
import com.geip.dto.SummonerDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@Service
public class RiotApiService {

    @Value("${RiotApiKey}")
    private String apiKey;

    @Transactional
    public MainSummonerDTO SearchSummonerName(String name) throws JsonProcessingException {
        String requestURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + name + "?api_key=" + apiKey;
        String result = WebClient.create(requestURL)
                .get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();
        SummonerDTO summonerDTO = mapper.readValue(result, SummonerDTO.class);

        String iconURL = "http://ddragon.leagueoflegends.com/cdn/12.18.1/img/profileicon/"+summonerDTO.getProfileIconId()+".png";
//        byte[] icon = WebClient.create(iconURL)
//                .get()
//                .accept(MediaType.IMAGE_PNG)
//                .retrieve()
//                .bodyToMono(byte[].class)
//                .block();


        return MainSummonerDTO.builder()
                .name(summonerDTO.getName())
                .summonerLevel(summonerDTO.getSummonerLevel())
                .profileImg(iconURL)
                .build();
    }
}
