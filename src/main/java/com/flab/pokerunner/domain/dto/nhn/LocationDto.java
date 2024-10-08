package com.flab.pokerunner.domain.dto.nhn;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LocationDto {
    private Header header;
    private Location location;

    @Getter
    @ToString
    public static class Header {
        private boolean isSuccessful;
        private int resultCode;
        private String resultMessage;
    }

    @Getter
    @ToString
    public static class Location {
        private boolean result;
        private boolean hasAdmAddress;
        private Adm adm;
        @JsonProperty("adm_address")
        private AdmAddress admAddress;
        @JsonProperty("legal_address")
        private LegalAddress legalAddress;
    }

    @Getter
    @ToString
    public static class Adm {
        private String posx;
        private String posy;
        private String address;
        private int distance;
        private String bldname;
        private String bldnum;
        private String admcode;
        private String postcode;
        private String jibun;
        private String roadname;
        private String roadjibun;
    }

    @Getter
    @ToString
    public static class AdmAddress {
        private String address;
        private String admcode;
        @JsonProperty("address_category3")
        private String addressCategory3;
        @JsonProperty("address_category4")
        private String addressCategory4;
        private String jibun;
        @JsonProperty("address_category1")
        private String addressCategory1;
        @JsonProperty("address_category2")
        private String addressCategory2;
        @JsonProperty("cut_address")
        private String cutAddress;
    }

    @Getter
    @ToString
    public static class LegalAddress {
        private String address;
        private String admcode;
        @JsonProperty("address_category3")
        private String addressCategory3;
        @JsonProperty("address_category4")
        private String addressCategory4;
        private String jibun;
        @JsonProperty("address_category1")
        private String addressCategory1;
        @JsonProperty("address_category2")
        private String addressCategory2;
        @JsonProperty("cut_address")
        private String cutAddress;
    }
}
