package com.pagonext.sbreadingwriting.domain.repository.primary;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pagonext.sbreadingwriting.domain.model.TripCsvLine;

@Repository
public interface TripRepository extends MongoRepository<TripCsvLine, String> {

}