package com.flab.pokerunner.service;

import com.flab.pokerunner.domain.dto.nhn.CoordinatesDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class NHNMapServiceTest {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    NHNMapService nhnMapService;

    @Test
    void getCoordinatesByAddressTest() {
        CoordinatesDto address = nhnMapService.getCoordinatesByAddress("봉천동 877-10");
        log.info("address:{}", address);
    }
}