package com.flab.pokerunner.service.running;

import com.flab.pokerunner.domain.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class ReverseGeocoding {

    private final Environment env;
    private final RestClient restClient;

    /**
     * 구 주소만 가져온다.
     */
    public LocationDto getLocationData(double lat, double lng) {
        String url = env.getProperty("nhn.map.url");

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
}
