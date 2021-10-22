package kr.co.korea.airquality.airquality.infrastructure.api;

// static method 만 있는 클래스
public class AirQualityLevel {

    // 인스턴스 만들지못하게 막음
    // 기본생성자는 원해 컴파일러가 자동추가하지만, 명시적으로 private 생성자를 추가하면 인스턴스생성을 막는다
    private AirQualityLevel() {}

    // 지수 범위를 지정하고 좋음, 보통, 나쁨, 매우나쁨으로 구분하자
    // 타입은 같지만 종류와 지수 범위가 다르기때문에 각각 한개씩 만들어야겠다

    public static String getPm10Level(Float pm10){
        if(pm10 <= 30) return "좋음";
        if(pm10 <= 80) return "보통";
        if(pm10 <= 150) return "나쁨";
        return "매우나쁨";
    }

    public static String getPm25Level(Float pm25){
        if(pm25 <= 15) return "좋음";
        if(pm25 <= 35) return "보통";
        if(pm25 <= 75) return "나쁨";
        return "매우나쁨";
    }

    public static String getO3Level(Float o3){
        if(o3<=0.030) return "좋음";
        if(o3 <= 0.090) return "보통";
        if(o3 <= 0.150) return "나쁨";
        return "매우나쁨";
    }

    public static String getNO2Level(Float no2){
        if(no2<=0.030) return "좋음";
        if(no2 <= 0.060) return "보통";
        if(no2 <= 0.200) return "나쁨";
        return "매우나쁨";
    }

    public static String getCOLevel(Float co){
        if(co<=2) return "좋음";
        if(co <= 9) return "보통";
        if(co <= 15) return "나쁨";
        return "매우나쁨";
    }

    public static String getSO2Level(Float so2){
        if(so2<=0.020) return "좋음";
        if(so2 <= 0.050) return "보통";
        if(so2 <= 0.150) return "나쁨";
        return "매우나쁨";
    }


}
