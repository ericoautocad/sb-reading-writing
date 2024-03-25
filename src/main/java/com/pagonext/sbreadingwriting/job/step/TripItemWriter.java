package com.pagonext.sbreadingwriting.job.step;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.pagonext.sbreadingwriting.domain.model.TripChosen;
import com.pagonext.sbreadingwriting.runner.ScheduledJobLauncher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TripItemWriter implements ItemWriter<TripChosen>, StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledJobLauncher.class);
    
    private StepExecution stepExecution;

    private int totalWriteTrip = 0;

    private final List<TripChosen> writeTrips = new ArrayList<>();

	private final DateFormat fileDateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm");

    private static final String CSV_HEADER = "bikeID,Age,Gender,TripDuration,StartStation,EndStation";

    private MongoTemplate template;

    public TripItemWriter(@Autowired MongoTemplate template) {

        super();

        this.template = template;

    }


    @Override
    public void write(Chunk<? extends TripChosen> tripsChunk) {
        totalWriteTrip += tripsChunk.getItems().size();

        writeTrips.addAll(tripsChunk.getItems());
        for(int index=0;  index < writeTrips.size(); index++) {
             Date date = new Date();
            writeTrips.get(index).setId(new ObjectId(date, index).get().toString());
        }

        template.insertAll(writeTrips);
    }

}
