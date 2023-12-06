package com.example.astra.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.astra.model.FruitablesPrimaryKey;
import com.example.astra.model.fruitables;

public interface FruitablesRepository extends CassandraRepository<fruitables, FruitablesPrimaryKey>{

	@AllowFiltering
    @Query("SELECT * FROM agri_keyspace.fruitables WHERE time > :time")
    List<fruitables> findAllByTimeGreaterThan(@Param("time") Instant time);
}
