package com.mkyong;

import java.util.Comparator;

public class Bidder{

	int id;
	double quote;
	String name;
	String type;
	String brand;


	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getQuote() {
		return quote;
	}

	public void setQuote(double quote) {
		this.quote = quote;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "Bidder [id=" + id + ", quote=" + quote + ", name=" + name
				+ ", type=" + type + ", brand=" + brand + "]";
	}

	

}
