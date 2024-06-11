package com.qwammy.javaflupskilling_week_5.exceptions.product;

import com.qwammy.javaflupskilling_week_5.exceptions.AppException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends AppException {

    public ProductNotFoundException() {
        this("Product Not Found");
    }

    public ProductNotFoundException(String message) {
        super(message);
        setStatusCode(HttpStatus.NOT_FOUND.value());
    }
}
