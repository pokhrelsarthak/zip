package com.example.astra.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value="fruitables")
public class fruitables {

	@PrimaryKey
    private FruitablesPrimaryKey primaryKey;

    @Column("color")
    private String color;

    @Column("current_price")
    private float currentPrice;

    @Column("prev_price")
    private float prevPrice;

    @Column("price_change")
    private String priceChange;

	public fruitables() {
		super();
	}

	public fruitables(FruitablesPrimaryKey primaryKey, String color, float currentPrice, float prevPrice,
			String priceChange) {
		super();
		this.primaryKey = primaryKey;
		this.color = color;
		this.currentPrice = currentPrice;
		this.prevPrice = prevPrice;
		this.priceChange = priceChange;
	}

	public FruitablesPrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(FruitablesPrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public float getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(float currentPrice) {
		this.currentPrice = currentPrice;
	}

	public float getPrevPrice() {
		return prevPrice;
	}

	public void setPrevPrice(float prevPrice) {
		this.prevPrice = prevPrice;
	}

	public String getPriceChange() {
		return priceChange;
	}

	public void setPriceChange(String priceChange) {
		this.priceChange = priceChange;
	}
    
    
    
}
