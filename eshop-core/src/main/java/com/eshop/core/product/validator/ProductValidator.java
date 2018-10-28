package com.eshop.core.product.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.eshop.core.product.model.Product;


public class ProductValidator implements Validator{

    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "name.required");

        Product product = (Product) o;

        if( product.getGroup() == null ){
            errors.rejectValue("group", "group.required");
        }

        if( product.getUserId() == null ){
            errors.rejectValue("userId", "user.required");
        }

    }
}
