package com.pagonext.sbreadingwriting.job;

import static com.pagonext.sbreadingwriting.domain.constant.BatchConstants.*;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import com.pagonext.sbreadingwriting.domain.document.Trips;
import com.pagonext.sbreadingwriting.domain.model.TripChosen;
import com.pagonext.sbreadingwriting.job.step.TripItemProcessor;
import com.pagonext.sbreadingwriting.job.step.TripItemReader;
import com.pagonext.sbreadingwriting.job.step.TripItemWriter;
import com.pagonext.sbreadingwriting.job.step.TripStepListener;


@Configuration
public class JobConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobConfig.class);

    // @Autowired
    // @Qualifier("mongoSecondaryTemplate")
    // private MongoTemplate mongoSecondaryTemplate;

    @Bean
    public DataSource getDataSource() {
        return new EmbeddedDatabaseBuilder()
                .addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }


    @Bean
    public Job tripJob(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                       @Qualifier("primaryMongoTemplate") MongoTemplate mongoTemplateSource,
                       @Qualifier("secondaryMongoTemplate") MongoTemplate mongoTemplateTarget,
                       @Value("#jobExecutionContext['fileName']") String fileName) {
        return new JobBuilder("tripJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(tripJobStep(jobRepository, transactionManager, mongoTemplateSource, mongoTemplateTarget))
                .listener(new TripJobCompletionListener())
                .build();
    }

    @Bean
    public Step tripJobStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                            MongoTemplate mongoTemplateSource, MongoTemplate mongoTemplateTarget) {
        return new StepBuilder("tripJobCSVGenerator", jobRepository)
                .startLimit(DEFAULT_LIMIT_SIZE)
                .<Trips, TripChosen>chunk(DEFAULT_CHUNK_SIZE, transactionManager)

                .reader(new TripItemReader(mongoTemplateSource))
                .processor(new TripItemProcessor())
                .writer(new TripItemWriter(mongoTemplateTarget))
                .listener(new TripStepListener())
                .build();
    }

}