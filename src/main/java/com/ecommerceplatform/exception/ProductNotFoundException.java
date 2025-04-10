package com.ecommerceplatform.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String text) {
        super(text);
    }
}
