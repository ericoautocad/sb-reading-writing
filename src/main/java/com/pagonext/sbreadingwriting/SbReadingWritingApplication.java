package com.pagonext.sbreadingwriting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
/*@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableMongoRepositories*/
@SpringBootApplication
public class SbReadingWritingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbReadingWritingApplication.class, args);
	}

}
