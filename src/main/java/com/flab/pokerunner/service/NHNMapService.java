package com.flab.pokerunner.service;

import com.flab.pokerunner.domain.dto.nhn.CoordinatesDto;
import com.flab.pokerunner.domain.dto.nhn.LocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class NHNMapService {

    private final Environment env;
    private final RestClient restClient;

    /**
     * 구 주소만 가져온다.
     */
    public LocationDto getLocationData(double lat, double lng) {
        String url = env.getProperty("nhn.map.url") + "/addresses";

        int coordtype = 1;

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("posY", lat)
                .queryParam("posX", lng)
                .queryParam("coordtype", coordtype)
                .encode()
                .toUriString();

        LocationDto locationDto = null;
        try {
            locationDto = restClient.get()
                    .uri(urlTemplate)
                    .retrieve()
                    .body(LocationDto.class);
        } catch (Exception e) {
        }

        return locationDto;
    }

    public CoordinatesDto getCoordinatesByAddress(String address) {
        String url = env.getProperty("nhn.map.url");

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .path("/coordinates")
                .queryParam("query", address)
                .build(false)
                .toUriString();

        CoordinatesDto coordinatesDto = null;
        try {
            coordinatesDto = restClient.get()
                    .uri(urlTemplate)
                    .retrieve()
                    .body(CoordinatesDto.class);
        } catch (Exception e) {
        }
        
        return coordinatesDto;
    }
}
