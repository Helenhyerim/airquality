package kr.co.korea.airquality.airquality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication 내의 @ComponentScan 을 통해 서버 실행
// Component (빈)들을 스캔하는데 main class 부터 아래로 패키지들을 따라가면서 스캔

@SpringBootApplication
public class AirqualityApplication {

	public static void main(String[] args) {

		SpringApplication.run(AirqualityApplication.class, args);
	}

}
