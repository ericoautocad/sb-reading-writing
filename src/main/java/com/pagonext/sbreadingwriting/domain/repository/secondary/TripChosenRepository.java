package com.pagonext.sbreadingwriting.domain.repository.secondary;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pagonext.sbreadingwriting.domain.model.TripChosen;

@Repository
public interface TripChosenRepository extends MongoRepository<TripChosen, String> {

}
