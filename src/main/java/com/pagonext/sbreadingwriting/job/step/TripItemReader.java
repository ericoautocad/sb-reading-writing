package com.pagonext.sbreadingwriting.job.step;

import static com.pagonext.sbreadingwriting.domain.constant.BatchConstants.*;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.data.MongoCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import com.pagonext.sbreadingwriting.domain.document.Trips;
import com.pagonext.sbreadingwriting.domain.enums.JobParametersKey;

@Component
public class TripItemReader extends MongoCursorItemReader<Trips> implements StepExecutionListener {

    private String sourceEntity;

    private static final Logger LOGGER = LoggerFactory.getLogger(TripItemReader.class);

    /*
     * """
     * {
     * "birth year": { "$ne": "" },
     * "usertype": { "$eq": "Subscriber" },
     * "tripduration": { "$gt": 500 },
     * "$expr": {
     * "$ne": ["$start station name",
     * "$end station name"]
     * }
     * }
     * """
     */
    public TripItemReader(@Autowired MongoTemplate mongoTemplate) {

        Criteria criteria = Criteria.where("birth year").ne("").and("usertype").is("Subscriber").and("tripduration")
                .gt(500);
        BasicQuery query = new BasicQuery("{ $expr: {'$ne': ['$start station name', '$end station name']}}");
        query.addCriteria(criteria);

        setName("reader");
        setTargetType(Trips.class);
        setTemplate(mongoTemplate);
        setCollection(sourceEntity);
        setBatchSize(DEFAULT_CHUNK_SIZE);
        setQuery(query);
        setLimit(DEFAULT_LIMIT_SIZE);
        Map<String, Sort.Direction> sortOptions = new HashMap<>();
        sortOptions.put("birth year", Sort.Direction.ASC);
        setSort(sortOptions);

    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

        JobParameters jobParameters = stepExecution.getJobParameters();
        String entity = jobParameters.getString(JobParametersKey.SOURCE_ENTITY.getKey());

        this.sourceEntity = entity;

    }

}
