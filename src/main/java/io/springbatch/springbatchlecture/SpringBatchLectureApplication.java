package io.springbatch.springbatchlecture;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing // Spring batch에 enable로 시작하는 배치가 많다. EnableWebMvc, EnableSpringSecurity,…
// 보통 Enable로 시작하는 어노테이션은 그 기능이 초기화할수있도록 활성화하는 역할을 함.
// 보통 메인 애플리케이션위에 선언
public class SpringBatchLectureApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchLectureApplication.class, args);
    }

}
