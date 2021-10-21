package kr.co.korea.airquality.airquality.interfaces.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

public class SeoulAirQualityApiDtoIn {

    @Getter
    @Setter
    @ToString
    public static class GetAirQualityResponse{
        @JsonProperty("DailyAverageCityAir")
        private Results results;
    }

    @Getter
    @Setter
    @ToString
    public static class Results {
        @JsonProperty("RESULT")
        private Header header;
        @JsonProperty("row")
        private List<Result> result;

        public boolean isSuccess() {
            if (Objects.equals(header.getCode(), "INFO-000")) {
                return true;
            }
            return false;
        }

    }

    @Getter // 클래스레벨에 선언시 모든필드에 Getter 자동생성(Lombok library)
    @Setter // 클래스레벨에 선언시 모든필드에 Setter 자동생성(Lombok library)
    @ToString // 클래스레벨에 선언시 모든필드 toString화 됨, 제외도 가능
    public static class Header { // 코드와 메세지
        @JsonProperty("CODE")
        private String code;
        @JsonProperty("MESSAGE")
        private String message;
    }

    @Getter
    @Setter
    @ToString
    public static class Result { // 결과값
        // JsonProperty: 서로 다른 snake, camel 케이스의 key를 일치시켜주기 위함
        // Json 객체를 내려보낼때 실제 클래스의 필드명과 다르게 내보내기 위함
        // 받거나 내보낼때는 JsonProperty("__")의 형태로, 클래스에서는 필드명으로 활동
        @JsonProperty("MSRDT_DE")
        private String date;
        @JsonProperty("MSRRGN_NM")
        private String zone;
        @JsonProperty("MSRSTE_NM")
        private String district;

        @JsonProperty("PM10")
        private Float pm10;
        @JsonProperty("PM25")
        private Float pm25;
        @JsonProperty("O3")
        private Float o3;
        @JsonProperty("NO2")
        private Float no2;
        @JsonProperty("CO")
        private Float co;
        @JsonProperty("SO2")
        private Float so2;
    }
}