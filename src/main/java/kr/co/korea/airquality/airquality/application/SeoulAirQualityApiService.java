package kr.co.korea.airquality.airquality.application;//package kr0.co.korea.airquality.airquality;
//
//import lombok.AllArgsConstructor;

import kr.co.korea.airquality.airquality.infrastructure.api.SeoulAirQualityApiCaller;
import kr.co.korea.airquality.airquality.interfaces.api.SeoulAirQualityApiDtoOut;
import org.springframework.stereotype.Service;


// 트랜잭션, 도메인간 순서 보장
@Service
//@AllArgsConstructor // 의존성주입
// 둘중에 하나만 final만
public class SeoulAirQualityApiService {

    // 생성자로 의존성 주입
    private final SeoulAirQualityApiCaller seoulAirQualityAPICaller;
    // 이걸 대체하는 annotation 이 @RequiredArgsConstructor/@AllArgsConstructor
    public SeoulAirQualityApiService(SeoulAirQualityApiCaller seoulAirQualityAPICaller) {
        this.seoulAirQualityAPICaller = seoulAirQualityAPICaller;
    }

    // getairquality 하는 메소드
    public SeoulAirQualityApiDtoOut.GetAirQualityInfo getAirQuality(){

        // Caller 클래스의 메소드 호출
        var airQualityInfo = seoulAirQualityAPICaller.getAirQuality();
        return airQualityInfo;

    }

}
