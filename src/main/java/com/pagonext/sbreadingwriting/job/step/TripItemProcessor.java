package com.pagonext.sbreadingwriting.job.step;


import java.time.Duration;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.pagonext.sbreadingwriting.domain.document.Trips;
import com.pagonext.sbreadingwriting.domain.enums.UserGender;
import com.pagonext.sbreadingwriting.domain.model.TripChosen;
import com.pagonext.sbreadingwriting.runner.ScheduledJobLauncher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TripItemProcessor implements ItemProcessor<Trips, TripChosen> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledJobLauncher.class);

	@Override
	public TripChosen process(Trips item) {

		var age = LocalDate.now().getYear() - item.getBirthYear();
		var gender = UserGender.getType(item.getGender()).name();
		Duration duration = Duration.ofSeconds(item.getDuration());
		String formattedDuration= String.format("%02d:%02d:%02d", duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart());

		return new TripChosen(item.getBikeId(), age, gender, formattedDuration, item.getStartStationName(), item.getEndStationName());
	}
}