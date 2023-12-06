package com.example.astra.model;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class FruitablesPrimaryKey implements Serializable{

	@PrimaryKeyColumn(name = "name", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String name;

    @PrimaryKeyColumn(name = "time", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private Instant time;
    
	public FruitablesPrimaryKey() {
		super();
	}
	public FruitablesPrimaryKey(String name, Instant time) {
		super();
		this.name = name;
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Instant getTime() {
		return time;
	}
	public void setTime(Instant time) {
		this.time = time;
	}

    
    
   @Override
    public boolean equals(Object o) {
	   if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;

	    FruitablesPrimaryKey agriKey = (FruitablesPrimaryKey) o;

	    // Custom logic to compare attributes
	    return !name.equals(agriKey.name) || !time.equals(agriKey.time);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
