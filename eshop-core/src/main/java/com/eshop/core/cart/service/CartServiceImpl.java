package com.eshop.core.cart.service;

import com.eshop.commons.Cache;
import com.eshop.core.cart.model.CartItem;
import com.eshop.core.order.OrderRepository;
import com.eshop.core.order.model.Order;
import com.eshop.core.order.model.OrderItem;
import com.eshop.core.product.GroupRepository;
import com.eshop.core.product.ProductRepository;
import com.eshop.core.product.model.GroupVariant;
import com.eshop.core.product.model.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
    ProductRepository productRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    OrderRepository orderRepository;

   /* @Autowired
    private SessionFactory sessionFactory;*/

    @Autowired
    private Cache cache;

    @Override
    public String createNewCart() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void addProduct(String cartId, CartItem cartItem) {
        cache.addItemToList(cartId, cartItem);
    }

    @Override
    public void removeProduct(String cartId, String productId) {
        CartItem itemToRemove = new CartItem();

        // We use only product ID since it's the one being compared in CartItem.equals() method:
        itemToRemove.setProductId(Long.parseLong(productId));

        cache.removeItemFromList(cartId, itemToRemove);
    }

    @Override
    public void setProductQuantity(String cartId, String productId, int quantity){

        List<CartItem> list = (List) cache.getList(cartId, CartItem.class);
        list.forEach( cartItem -> {
            try{
                if(cartItem.getProductId() == Long.parseLong(productId) ){
                    cartItem.setQuantity( quantity );
                    cache.removeItemFromList(cartId, cartItem);
                    cache.addItemToList(cartId, cartItem);
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        } );
    }

    @Override
    public Set<CartItem> getItems(String cartId){
        return new HashSet<CartItem>( (List) cache.getList(cartId, CartItem.class));
    }

    @Override
    public Order createOrder(String cartId, Order order) {
        List<CartItem> cartItems = (List)cache.getList(cartId, CartItem.class);

        order = addCartItemsToOrders(cartItems, order);
        if( order == null){
            System.out.println("Order not set.");
        }
        
        order = orderRepository.save(order);

        cache.removeItem(cartId);

        return order;
    }

    private Order addCartItemsToOrders(List<CartItem> cartItems, Order order){

        cartItems.forEach(cartItem -> {
        	
        	Optional<Product> product = productRepository.findById(cartItem.getProductId());
            Product prod =  product.get();
            int qty = cartItem.getQuantity() > 0 ? cartItem.getQuantity() : 1;
            long variantId = cartItem.getVariantId();

            for(int i = 0; i < qty; i++) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(prod);
                if(variantId > 0) {
                    GroupVariant v = new GroupVariant();
                    v.setId(variantId);
                    orderItem.setGroupVariant(v);
                }
                orderItem.setOrder(order);
                order.getItems().add(orderItem);
            }

        } );

        return order;
    }

    private String generateCartRedisId(String cartId){
        return "cart#"+cartId+"#items";
    }
}