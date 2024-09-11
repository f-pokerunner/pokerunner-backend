package com.flab.pokerunner.domain.dto.nhn;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class CoordinatesDto {
    private AddressData address;
    private Header header;

    @Getter
    @ToString
    public static class AddressData {
        private boolean result;
        private int totalcount;
        private int admtotalcount;
        private int admcount;
        private int recommnededCost;
        private String res_type;
        private List<AdmData> adm;
    }

    @Getter
    @ToString
    public static class AdmData {
        private int type;
        private String posx;
        private String posy;
        private String admcode;
        private String address;
        private String jibun;
        private String roadname;
        private String roadjibun;
        private String postcode;
        private String buildname;
        private int accuracy;
        private int distance;
    }

    @Getter
    @ToString
    public static class Header {
        private boolean isSuccessful;
        private int resultCode;
        private String resultMessage;
    }
}

