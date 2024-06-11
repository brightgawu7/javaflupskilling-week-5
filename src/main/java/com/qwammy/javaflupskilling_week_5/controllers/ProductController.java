package com.qwammy.javaflupskilling_week_5.controllers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.qwammy.javaflupskilling_week_5.dtos.ApiResponse;
import com.qwammy.javaflupskilling_week_5.dtos.product.request.CreateProductDto;
import com.qwammy.javaflupskilling_week_5.dtos.product.response.ReadProductDto;
import com.qwammy.javaflupskilling_week_5.exceptions.product.ProductNotFoundException;
import com.qwammy.javaflupskilling_week_5.services.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@PreAuthorize("isFullyAuthenticated()")
public class ProductController {
    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<ReadProductDto>> createProduct(@RequestBody @Valid CreateProductDto createProductDto) {

        ApiResponse<ReadProductDto> response = ApiResponse
                .success(
                        productService.createProduct(createProductDto),
                        "Product created successfully",
                        HttpStatus.CREATED.value()
                );
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReadProductDto>> getProductById(@PathVariable UUID id) throws ProductNotFoundException {

        ApiResponse<ReadProductDto> response = ApiResponse
                .success(
                        productService.getProductById(id),
                        "",
                        HttpStatus.OK.value()
                );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<ReadProductDto>>> getAllProducts() {

        ApiResponse<List<ReadProductDto>> response = ApiResponse
                .success(
                        productService.getAllProducts(),
                        "",
                        HttpStatus.OK.value()
                );
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReadProductDto>> updateProduct(@PathVariable UUID id, @RequestBody CreateProductDto createProductDto) throws JsonMappingException, ProductNotFoundException {

        ApiResponse<ReadProductDto> response = ApiResponse
                .success(
                        productService.updateProduct(id, createProductDto),
                        "Product updated successfully",
                        HttpStatus.OK.value()
                );
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteProduct(@PathVariable UUID id) throws ProductNotFoundException {
        productService.deleteProduct(id);
        ApiResponse<Object> response = ApiResponse
                .success(
                        null,
                        "Product deleted successfully",
                        HttpStatus.NO_CONTENT.value()
                );
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
