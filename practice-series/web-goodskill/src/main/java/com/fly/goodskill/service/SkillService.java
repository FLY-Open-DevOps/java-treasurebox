package com.fly.goodskill.service;

/**
 * <p>
 *
 * 
 * @Date 2019/3/2011:36
 */
public interface SkillService {

    String querySkillProductInfo(String productId);

    String skillProduct(String productId);

    String skillProductByRedis(String productId);
}
