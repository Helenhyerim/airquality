package kr.co.korea.airquality.airquality.infrastructure.api;

import kr.co.korea.airquality.airquality.infrastructure.api.SeoulAirQualityApi;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.korea.airquality.airquality.interfaces.api.SeoulAirQualityApiDtoIn;
import kr.co.korea.airquality.airquality.interfaces.api.SeoulAirQualityApiDtoOut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;
import java.util.stream.*;
import java.io.IOException;

@Slf4j //로그
@Component // 빈등록
public class SeoulAirQualityApiCaller {
    // 인터페이스 final
    private final SeoulAirQualityApi seoulAirQualityAPI;

    //생성자
    // @Value("${__}") : properties 파일에 등록된 정보 읽어옴 -> String baseUrl 에 저장
    public SeoulAirQualityApiCaller(@Value("${api.Seoul.baseUrl}") String baseUrl){
        // Json-> Java 객체로 Deserialization하기
        // ObjectMapper(Json ->Object 형태로 도와주는) 객체생성,
        // request json!!!
        ObjectMapper objectMapper = new ObjectMapper();
        // 그리고 ObjectMapper 에 Feature 설정, Json->Object(역직렬화) 변환시 존재하지않는 property가 있는경우
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Retrofit: 네트워크로부터 전달된 데이터를 필요한 형태의 객체로 받게해주는 library
        // interface 에 기술된명세를 http api 로 바꿔줌
        // .baseUrl(요청할 서버의 기본URL)
        // .addConverterFactory() : Json->POJO클래스 형식으로 자동변환하는 데이터파싱 converter 추가, json 형태로 데이터 변환제공
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper)).build();
        // retrofit 인스턴스를 이용해 interface 구현!!!(retrofit 과 interface 연결)
        this.seoulAirQualityAPI = retrofit.create(SeoulAirQualityApi.class);
    }

    //Retrofit 객체를 사용해서 Service에 선언한 메서드를 호출하면 Call객체를 받는다.
    //이렇게 Call객체를 받은 것만으로 통신이되는 것이 아니라
    //Call객체의 enqueue 메서드를 호출해야 해당 시점에서 API 통신을 하기 때문이다.
    //실제 개발 시 재사용성을 높이고 유지보수를 편하게 하기위해
    //Retrofit을 생성하는 중간 클래스인 Repository를 만들어 두고
    //필요 따른 Retrofit 생성 메서드를 저장한다.

    // 메소드 (인터페이스 Call getAirQuality 구현부분)
    public SeoulAirQualityApiDtoOut.GetAirQualityInfo getAirQuality(){
        try{
            // 통신 완료 후 callback 받기 위함
            // response json!!!
            var call = seoulAirQualityAPI.getAirQuality();
            // response json 이 deserialized 된 ResponseJson 객체
            var response = call.execute().body(); //역직렬화된 body부분을 띄운다


            // 받아온게 없거나 받아올 Results 가 없으면 throw
            if(response == null || response.getResults() == null){
                throw new RuntimeException("getAirQuality 응답값이 존재하지 않습니다");
            }

            // 받아올 Results 가 성공했다면 return fromInToOut(DtoIn의 body부분)
            if(response.getResults().isSuccess()) {
                log.info(response.toString());
                return fromInToOut(response);
            }

            throw new RuntimeException("getAirQuality 응답이 올바르지 않습니다");

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("getAirQuality API error 발생! errorMessage=" + e.getMessage());
        }
    }

    // In -> Out 으로 한 목록씩 전환
    // DtoIn 의 List를 stream 으로 하나씩 돌려서 DtoOut의 List 로 바꾸기
    private List<SeoulAirQualityApiDtoOut.AirQualityOfDistrict> convert(List<SeoulAirQualityApiDtoIn.Result> results){

        // List<__> 형식이 아니면 stream() 사용불가
        return results.stream()
                .map(one -> new SeoulAirQualityApiDtoOut.AirQualityOfDistrict( // one.get@@ 는 DtoOut에 생성자 있어야함
                        one.getDistrict(),
                        one.getPm10(),
                        one.getPm25(),
                        one.getO3(),
                        one.getNo2(),
                        one.getCo(),
                        one.getSo2())
                ).collect(Collectors.toList());
    }

    // averagePM10 $ averagePM10Level 만들어야함


    // ***** 이 부분이 DtoIn 을 DtoOut으로 만드는 핵심 ********************************88
    // DtoIn 의 List를 위의 convert 메소드를 통해 DtoOut 의 List 로 변환하고 build 해서 반환하는 메소드
    public SeoulAirQualityApiDtoOut.GetAirQualityInfo fromInToOut(
            SeoulAirQualityApiDtoIn.GetAirQualityResponse InResults){
        // List<Result> 가져와서 변수에 담기
        var header = InResults.getResults().getHeader();
        var results = InResults.getResults().getResult();


        return SeoulAirQualityApiDtoOut.GetAirQualityInfo.builder()
                //.zone(getAirQuality().getZone()) // ?질문: results.getZone() 안되는 이유 & 무한로딩
                .resultMessage(header.getMessage())
                .airQualityByDistricts(convert(results))
                .build();

    }


}