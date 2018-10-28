package com.eshop.core.order;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.eshop.core.order.model.Order;


public class OrderValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Order.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");

        Order order = (Order) o;

        if( order.getName().length() < 2 ){
            errors.rejectValue("name", "name.required");
        }

        if(order.getItems()==null || order.getItems().size() < 1){
            errors.rejectValue("items", "items.required");
        }
    }
}