package com.mkyong;

import java.util.List;



public class ProductsList {
	
	private List<Product> products;

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "ProductsList [products=" + products + ", getProducts()="
				+ getProducts() + "]";
	}

	
	

}
