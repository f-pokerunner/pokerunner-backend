package com.flab.pokerunner.service.running;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReverseGeocodingTest {

    @Autowired
    ReverseGeocoding reverseGeocoding;

    @Test
    public void reverseGeocodingTest() {
        assertThat(reverseGeocoding.getGuAddress(37.5665, 126.9780)).isEqualTo("중구");
    }
}