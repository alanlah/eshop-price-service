package com.eshop.eureka.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.eshop.eureka.mapper.ProductPriceMapper;
import com.eshop.eureka.pojo.ProductPrice;
import com.eshop.eureka.service.ProductPriceService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class ProductPriceServiceImpl implements ProductPriceService {

	@Autowired
	private ProductPriceMapper productPriceMapper;
	@Autowired
	private JedisPool jedisPool;

	public void add(ProductPrice productPrice) {
		productPriceMapper.add(productPrice);
		Jedis jedis = jedisPool.getResource();
		jedis.set("product_price_" + productPrice.getProductId(), JSONObject.toJSONString(productPrice));
	}

	public void update(ProductPrice productPrice) {
		productPriceMapper.update(productPrice);
		Jedis jedis = jedisPool.getResource();
		jedis.set("product_price_" + productPrice.getProductId(), JSONObject.toJSONString(productPrice));
	}

	public void delete(Long id) {
		ProductPrice productPrice = findById(id);
		productPriceMapper.delete(id);
		Jedis jedis = jedisPool.getResource();
		jedis.del("product_price_" + productPrice.getProductId());
	}

	public ProductPrice findById(Long id) {
		return productPriceMapper.findById(id);
	}

	@Override
	public ProductPrice findByProductId(Long productId) {
		Jedis jedis = jedisPool.getResource();
		String dataJSON = jedis.get("product_price_" + productId);
		if (dataJSON != null & !"".equals(dataJSON)) {
			JSONObject dataJSONObject = JSONObject.parseObject(dataJSON);
			dataJSONObject.put("id", "-1");
			return JSONObject.parseObject(dataJSONObject.toJSONString(), ProductPrice.class);
		} else {
			return productPriceMapper.findByProductId(productId);
		}
	}

}
