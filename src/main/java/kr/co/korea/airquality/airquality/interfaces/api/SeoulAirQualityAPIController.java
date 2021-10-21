package kr.co.korea.airquality.airquality.interfaces.api;

import kr.co.korea.airquality.airquality.application.SeoulAirQualityApiService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

// RestController : REST방식의 데이터처리 가능, @Controller+@RequestBody
// JSP같은 뷰를 만들어내는 대신에 Json형태로 객체데이터 반환함
@Slf4j
@RestController//Jsckson이 존재하면 JSON 형식의 문자열로 변환해서 응답
@RequestMapping("/air-condition/today") // 요청에 대한 메소드, url 매핑
@RequiredArgsConstructor // 생성자 자동생성 lombok, 의존성 주입
public class SeoulAirQualityAPIController {

    // 의존성 주입
    private final SeoulAirQualityApiService seoulAirQualityAPIService;

    // 시 까지만 썼을 경우 -> List 로 결과 반환
    @GetMapping("/")
    public SeoulAirQualityApiDtoOut.GetAirQualityInfo getSeveralAirQualityResponse() {
        // service 클래스의 getairQuality 메소드 호출

        return seoulAirQualityAPIService.getAirQuality();
    }
}
