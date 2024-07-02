package com.jsj.backend.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * FrcnRentInfo 배치 작업을 스케줄링하는 클래스.
 * 매일 새벽 1시에 배치 작업을 실행합니다.
 *
 * @Component 애노테이션을 사용하여 스프링 컨텍스트에서 빈으로 관리됩니다.
 * @Slf4j는 로깅을 위한 로거를 제공합니다.
 */
@Component
@Slf4j
public class FrcnRentInfoScheduler {
    private final JobLauncher jobLauncher;
    private final Job frcnRentInfoJob;

    /**
     * FrcnRentInfoScheduler 생성자.
     *
     * @param jobLauncher JobLauncher 객체
     * @param frcnRentInfoJob 배치 작업 객체
     */
    public FrcnRentInfoScheduler(
            JobLauncher jobLauncher,
            @Qualifier("frcnRentInfoJob") Job frcnRentInfoJob
    ) {
        this.jobLauncher = jobLauncher;
        this.frcnRentInfoJob = frcnRentInfoJob;
    }

    /**
     * 매일 새벽 1시에 배치 작업을 실행합니다.
     * 작업이 이미 실행 중이거나, 재시작할 수 없거나, 이미 완료된 경우를 처리합니다.
     */
    @Scheduled(cron = "0 0 1 * * ?") // 매일 새벽 1시에 실행
    public void saveFrcnRentInfo() {
        String jobName = frcnRentInfoJob.getName();
        log.info("{} 작업이 시작되었습니다.", jobName);
        try {
            // 현재 시간을 파라미터로 추가하여 JobParameters 생성
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            // 배치 작업 실행
            JobExecution jobExecution = jobLauncher.run(frcnRentInfoJob, jobParameters);
            log.info("Job ID: {} 상태: {}", jobExecution.getId(), jobExecution.getStatus());
        } catch (JobExecutionAlreadyRunningException e) {
            log.error("작업이 이미 실행 중입니다: {}", jobName, e);
        } catch (JobRestartException e) {
            log.error("작업을 재시작할 수 없습니다: {}", jobName, e);
        } catch (JobInstanceAlreadyCompleteException e) {
            log.error("작업이 이미 완료되었습니다: {}", jobName, e);
        } catch (Exception e) {
            log.error("작업 실행 중 오류가 발생했습니다: {}", jobName, e);
        }
    }
}
