package com.eshop.core.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eshop.core.order.model.Order;
import java.util.List;

@Service 
public class OrderService {
	
	    @Autowired
	    OrderRepository orderRepository;
	    
	    /* ORDERS */
	    public List<Order> getOrders(){
	        return orderRepository.findAll();
	    }
	    public Order getOrder(long id){
	        return orderRepository.findById(id).get();
	    }
	    public Order saveOrder(Order order){
	        return orderRepository.save(order);
	    }
}
