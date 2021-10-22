package kr.co.korea.airquality.airquality.interfaces.api.Seoul;

import kr.co.korea.airquality.airquality.interfaces.api.Seoul.SeoulAirQualityApiDtoIn;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SeoulAirQualityApi {
    String servicekey = "536c58687267757531334b46554b41";

    // retrofit 은 interface에 기술된 명세를 HTTP API로 전환해주므로
    // 요청할 API들에 대한 명세만을 interface에 기술해두면 된다
    // < > 안의 형태로 받겠다는 뜻으로 직접 구현해야함
    @GET(servicekey + "/json/DailyAverageCityAir/1/25/{date}") // date 필수값이므로 request 시 필요
    Call<SeoulAirQualityApiDtoIn.GetAirQualityResponse> getAirQuality(@Path("date") String date);
}

