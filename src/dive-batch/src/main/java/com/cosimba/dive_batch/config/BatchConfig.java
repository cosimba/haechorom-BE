package com.cosimba.dive_batch.config;

import com.cosimba.dive.domain.clean.entity.Clean;
import com.cosimba.dive_batch.processor.DailyAverageProcessor;
import com.cosimba.dive_batch.reader.TrashDataReader;
import com.cosimba.dive_batch.repository.AggregateResultRepository;
import com.cosimba.dive_batch.writer.DailyAverageWriter;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class BatchConfig extends DefaultBatchConfiguration {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public Job calculateAveragesJob(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    TrashDataReader trashDataReader,
                                    DailyAverageProcessor dailyAverageProcessor,
                                    DailyAverageWriter dailyAverageWriter) throws Exception {
        return new JobBuilder("calculateAveragesJob", jobRepository)
                .start(dailyAverageStep(jobRepository, transactionManager, trashDataReader, dailyAverageProcessor, dailyAverageWriter))
//                .next(monthlyAverageStep(jobRepository, transactionManager))
//                .next(yearlyAverageStep(jobRepository, transactionManager))
                .build();
    }

//    @Bean
//    public Step dailyAverageStep(JobRepository jobRepository,
//                                 PlatformTransactionManager transactionManager,
//                                 DailyAverageProcessor dailyAverageProcessor,
//                                 DailyAverageWriter dailyAverageWriter) throws Exception {
//        return new StepBuilder("dailyAverageStep", jobRepository)
//                .<Clean, Map<String, Double>>chunk(1, transactionManager) // Chunk size 설정
//                .reader(trashDataReader()) // ItemReader 사용
//                .processor(dailyAverageProcessor) // ItemProcessor (선택적)
//                .writer(dailyAverageWriter) // ItemWriter (선택적)
//                .transactionManager(transactionManager)
//                .build();
//    }

    @Bean
    public Step dailyAverageStep(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager,
                                 TrashDataReader trashDataReader,
                                 DailyAverageProcessor dailyAverageProcessor,
                                 DailyAverageWriter dailyAverageWriter) throws Exception {
        return new StepBuilder("dailyAverageStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    // 1. Reader로부터 모든 데이터를 읽어옴
                    List<Clean> cleanData = trashDataReader.readAll(); // TrashDataReader의 readAll 메서드 사용

                    // 2. Processor에서 전체 데이터를 처리
                    dailyAverageProcessor.process(cleanData); // 전체 Clean 데이터 처리

                    // 3. Processor에서 누적된 결과를 Writer에 전달하여 DB 저장
                    Map<String, Double> aggregatedData = dailyAverageProcessor.getResultMap(); // Processor에서 집계된 데이터 가져옴
                    dailyAverageWriter.setCoastResults(aggregatedData); // Writer에 결과 설정
                    dailyAverageWriter.write(); // Writer의 write 메서드 호출

                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .transactionManager(transactionManager)
                .listener(dailyAverageWriter) // Writer를 StepExecutionListener로 추가
                .build();
    }








//    @Bean
//    public JpaPagingItemReader<Clean> trashDataReader() throws Exception {
//        LocalDateTime now = LocalDateTime.now(); // 현재 날짜와 시간
//        LocalDateTime twentyFourHoursAgo = now.minusHours(24); // 24시간 전 시간;
//
//        JpaPagingItemReader<Clean> reader = new JpaPagingItemReaderBuilder<Clean>()
//                .name("trashDataReader")
//                .entityManagerFactory(entityManagerFactory)
//                .queryString("SELECT ct FROM Clean ct WHERE ct.cleanDate >= :startDate AND ct.cleanDate < :endDate")
//                .parameterValues(Map.of(
//                        "startDate", twentyFourHoursAgo,
//                        "endDate", now.toLocalDate().plusDays(1).atStartOfDay()
//                ))
//                .pageSize(10)
//                .build();
//        return reader;
//    }
//@Bean
//public JpaPagingItemReader<Clean> trashDataReader() throws Exception {
//    LocalDateTime now = LocalDateTime.now(); // 현재 날짜와 시간
//    LocalDateTime twentyFourHoursAgo = now.minusYears(7); // 24시간 전 시간;
//
//    JpaPagingItemReader<Clean> reader = new JpaPagingItemReaderBuilder<Clean>()
//            .name("trashDataReader")
//            .entityManagerFactory(entityManagerFactory)
//            .queryString("SELECT ct FROM Clean ct WHERE ct.cleanDate >= :startDate AND ct.cleanDate < :endDate")
//            .parameterValues(Map.of(
//                    "startDate", twentyFourHoursAgo,
//                    "endDate", now.toLocalDate().plusYears(1).atStartOfDay()
//            ))
//            .pageSize(10)
//            .build();
//    return reader;
//}
}
