package kr.co.korea.airquality.airquality.interfaces.api;

import kr.co.korea.airquality.airquality.infrastructure.api.AirQualityLevel;
import lombok.*;

import java.util.Collections;
import java.util.List;

public class SeoulAirQualityApiDtoOut {

    // Setter 없는 이유 :
    @Getter
    @Builder
    public static class GetAirQualityInfo{ // 시까지만 조회시 여러개, 구까지만 조회시 한개만 나오게
        private String zone; // ok
        private String resultMessage; // ok
        private String date; // ok
        private Double averagePM10; // ok
        private String averagePM10Level; // ok
        private List<AirQualityOfDistrict> airQualityByDistricts; // ok

        // 메소드
        public GetAirQualityInfo searchByDistrict(String district){
            // district 가 없으면, 즉 리스트 여러개 반환
            if(district == null){
                return this; // GetAirQualityInfo 을 리턴
            }

            // district 가 있으면, 즉 리스트 하나만 반환
            AirQualityOfDistrict OfDistrict = airQualityByDistricts.stream()
                    .filter(airQualityOfDistrict -> airQualityOfDistrict.getDistrict().equals(district))
                    .findFirst() // 리스트중 district 가 일치하는 것을 찾는데 그 중 첫번째 airQualityOfDistrict
                    .orElseThrow(()-> new IllegalArgumentException(district + "에 해당하는 구가 없습니다"));

            airQualityByDistricts = (List<AirQualityOfDistrict>) Collections.singleton(OfDistrict);
            return this;
        }
    }

    @Getter
    public static class AirQualityOfDistrict{
         // List 로 받을 구 정보
        // DtoIn 이름과 완전히 일치해야함
        private String district;

        private Float pm10;
        private String PM10Level;

        private Float pm25;
        private String PM25Level;

        private Float o3;
        private String O3Level;

        private Float no2;
        private String NO2Level;

        private Float co;
        private String COLevel;

        private Float so2;
        private String SO2Level;


        // stream map 사용하는데 왜 이 생성자가 필요할까
        public AirQualityOfDistrict(String district, Float pm10, Float pm25, Float o3, Float no2, Float co, Float so2) {
            this.district = district;
            this.pm10 = pm10;
            this.pm25 = pm25;
            this.o3 = o3;
            this.no2 = no2;
            this.co = co;
            this.so2 = so2;

            //AirQualityLevel airQualityLevel = new AirQualityLevel(); static method 만 있는 클래스는 인스턴스화하면안됨
            this.PM10Level = AirQualityLevel.getPm10Level(pm10);
            this.PM25Level= AirQualityLevel.getPm25Level(pm25);
            this.O3Level = AirQualityLevel.getO3Level(o3);
            this.NO2Level = AirQualityLevel.getNO2Level(no2);
            this.COLevel = AirQualityLevel.getCOLevel(co);
            this.SO2Level = AirQualityLevel.getSO2Level(so2);
        }

    }

}
