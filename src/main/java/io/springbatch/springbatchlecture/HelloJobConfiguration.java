package io.springbatch.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration // 설정 클래스니까 Configuration 어노테이션을 선언해야 여기서 빈을 생성할 수 있다.
public class HelloJobConfiguration { // Job을 정의한다? -> 아래 내용을 구성을 한다는 의미. 내용 자체가 잡을 실행할 수 있는 각각의 단위들을 설정하고 구성한다는 의미이다.

    // Job을 편리하게 구성할수 있는 몇가지 유틸성의 클래스들을 제공한다.
    // 예: JobBuilderFactory, StepBuilderFactory
    private final JobBuilderFactory jobBuilderFactory; // Job을 생성하는 빌더팩토리.
    private final StepBuilderFactory stepBuilderFactory; // Step을 생성하는 빌더팩토리.
    // new job, new step 이런 식의 생성이 아닌.. BuilderFactory의 도움을 받아 쉽게 Job, Step을 생성할수 있도록 유틸성의 클래스를 제공

    @Bean
    public Job helloJob() {
        return this.jobBuilderFactory.get("helloJob") // Job 생성. jobBuilderFactory.get("이름부여") <= 이것 자체가 잡을 생성하는 설정.
                .incrementer(new RunIdIncrementer())
                .start(step1()) // 인자로 Step이 들어감. // Job은 필수 요소로 Step을 가지고 있어야 한다. 가장 먼저 출발할 스텝을 start에 넣어준다.
                .next(step2()) // start 다음 스텝을 무엇으로 할 것이냐는 의미.
                .build(); // job의 구현체를 생성.
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1") // step 생성
                .tasklet(new Tasklet() { // step안에서 단일 task로 수행되는 로직을 구현. step 내에 tasklet 필수.
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("step1 has executed"); // 비즈니스 로직이 tasklet안에 구현되어 있음.
                        // 기본적으로 step은 tasklet을 무한반복시킨다.
                        // 특별한 경우외에는 무한반복이 되면 안되기때문에 RepeatStatus를 리턴해준다.
                        // return null은 한번 실행하고 종료.
                        return RepeatStatus.FINISHED; // return null과 같은 의미를 갖는 RepeatStatus이다.
                    }
                })
                .build();
    }
    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step2 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
