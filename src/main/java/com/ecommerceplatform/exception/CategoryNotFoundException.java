package com.ecommerceplatform.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String text) {
        super(text);
    }
}
