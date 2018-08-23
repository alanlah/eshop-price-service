package com.eshop.eureka.service;

import com.eshop.eureka.pojo.ProductPrice;

public interface ProductPriceService {
public void add(ProductPrice productPrice);
	
	public void update(ProductPrice productPrice);
	
	public void delete(Long id);
	
	public ProductPrice findById(Long id);

	public ProductPrice findByProductId(Long productId);
	
}
