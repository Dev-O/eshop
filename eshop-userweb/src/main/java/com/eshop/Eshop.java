package com.zshop.shopping;


/**
 * Hello world!
 *
 */
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.validation.Validator;
import redis.clients.jedis.Jedis;

//@ComponentScan("com.eshop")
@ComponentScan({"com.eshop.core", "com.eshop.core.cart.model","com.eshop.core.cart.service","com.eshop.core.order","com.eshop.core.order.model", "com.eshop.core.product","com.eshop.core.product.model"})
@SpringBootApplication
//@EnableConfigurationProperties(StorageProperties.class)
public class  Zshop
{
		public static void main(String[] args) {
			SpringApplication.run(Eshop.class, args);
		}

		/*@Bean
		CommandLineRunner init(StorageService storageService) {
			return (args) -> {
//				storageService.deleteAll();
				storageService.init();
			};
		}*/

	    @Bean
	    public HibernateJpaSessionFactoryBean sessionFactory() {
	        return new HibernateJpaSessionFactoryBean();
	    }

	    @Bean
	    public Validator productValidator(){
		    return new ProductValidator();
	    }
	    @Bean
	    public Validator groupValidator(){
	        return new GroupValidator();
	    }
	    @Bean
	    public Validator orderValidator(){
	        return new OrderValidator();
	    }
	   
	    
	    @Value("${redis.host}")
	    private String redisHost;
	    @Value("${redis.port}")
	    private int redisPort;
	    @Value("${redis.password}")
	    private String redisPassword;
//	    @Bean
	    private Jedis redisCliFactory(){
	        Jedis jedis = new Jedis(redisHost, redisPort);
	        jedis.auth(redisPassword);
	        return jedis;
	    }

	    @Bean
	    @Autowired
	    public Cache cacheObject(ObjectMapper objectMapper){
	        return new RedisCache(objectMapper, redisCliFactory());
	    }

	}

