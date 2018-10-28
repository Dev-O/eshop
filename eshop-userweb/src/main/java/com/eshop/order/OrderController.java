package com.eshop.order;

//import net.vatri.ecommerce.hateoas.OrderResource;
//import net.vatri.ecommerce.models.Order;
//import net.vatri.ecommerce.services.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.eshop.core.order.OrderResource;
import com.eshop.core.order.OrderService;
import com.eshop.core.order.model.Order;
import com.eshop.core.product.ProductService;

import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //@Autowired
    //private ProductService productService;
    
    @Autowired
    Validator orderValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.addValidators(orderValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<OrderResource> index() {
        List<Order> orders = orderService.getOrders();
        List<OrderResource> out = new ArrayList<OrderResource>();
        if(orders != null){
            orders.forEach(o -> {
                OrderResource orderResource = new OrderResource(o);
                orderResource.add(createHateoasLink(o.getId()));

                out.add(orderResource);
            });
        }
        return out;
    }

    @PostMapping
    public Order create(@RequestBody @Valid Order order){

        // Required by Hibernate ORM to save properly
        if(order.getItems() !=null){
            order.getItems().forEach(item -> item.setOrder(order));
        }
        return orderService.saveOrder(order);
    }

    @RequestMapping("/{id}")
    public OrderResource view(@PathVariable("id") long id){
        OrderResource orderResource = new OrderResource(orderService.getOrder(id));
        orderResource.add(createHateoasLink(id));
        return orderResource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Order edit(@PathVariable("id") long id, @RequestBody @Valid Order order){

        Order updatedOrder = orderService.getOrder(id);

        if(updatedOrder== null){
            return null;
        }


        return orderService.saveOrder(updatedOrder);
    }
    protected Link createHateoasLink(long id){
        Link link = linkTo(getClass()).slash(id).withSelfRel();
        return link;
    }
}
