package com.jsj.backend.batch.frcnRentInfo;

import com.jsj.backend.frcnRentInfo.entity.FrcnRentInfo;
import com.jsj.backend.frcnRentInfo.FrcnRentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Iterator;

/**
 * 농기계 임대 정보 배치 작업 설정 클래스.
 * 이 클래스는 Spring Batch를 사용하여 농기계 임대 정보를 처리하는 배치 작업을 구성합니다.
 */
@Configuration
@RequiredArgsConstructor
public class FrcnRentInfoBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager; // 트랜잭션 관리자 객체
    private final FrcnRentInfoService frcnRentInfoService;
    private final JdbcTemplate jdbcTemplate;

    /**
     * ItemReader 빈을 생성합니다.
     * 데이터를 읽어오는 작업을 정의
     * @return ItemReader 객체
     */
    @JobScope
    @Bean(name = "frcnRentInfoReader")
    public ItemReader<FrcnRentInfo> reader() {
        return new ItemReader<FrcnRentInfo>() {
            private final Iterator<FrcnRentInfo> dataIterator = frcnRentInfoService.getAllApiInfo().iterator();

            @Override
            public FrcnRentInfo read() {
                if (dataIterator.hasNext()) {
                    return dataIterator.next();
                } else {
                    return null; // 데이터의 끝에 도달했을 때 null을 반환하여 읽기 종료를 알림
                }
            }
        };
    }

    /**
     * ItemWriter 빈을 생성합니다.
     * DB에 작성하는 작업 정의
     * @return ItemWriter 객체
     */
    @Bean(name = "frcnRentInfoWriter")
    public ItemWriter<FrcnRentInfo> writer() {
        return new FrcnRentInfoWriter(jdbcTemplate);
    }

    /**
     * Step 빈을 생성합니다.
     * Job에서 시행하는 Step 중 하나
     * @return Step 객체
     */
    @Bean(name = "frcnRentInfoImportStep")
    public Step importStep() {
        return new StepBuilder("frcnRentInfoImport", jobRepository)
                .<FrcnRentInfo, FrcnRentInfo>chunk(1000, platformTransactionManager) // 한번에 처리하려는 레코드 라인 수
                .reader(reader())
//                .processor(processor()) 데이터 가공
                .writer(writer())
//                .taskExecutor(taskExecutor()) 비동기 처리
                .build();
    }

    /**
     * Job 빈을 생성합니다.
     * 빈 이름으로 Job 호출 및 실행
     * @return Job 객체
     */
    @Bean(name = "frcnRentInfoJob")
    public Job runJob() {
        return new JobBuilder("importFrcnRentInfo", jobRepository)
                .start(importStep()) // .next 를 사용하여 다음 작업 수행할 수도 있음
//                .listener(customJobExecutionListener) Job 전후 처리 작업 정의
                .build();
    }
}
