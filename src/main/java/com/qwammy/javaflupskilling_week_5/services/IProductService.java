package com.qwammy.javaflupskilling_week_5.services;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.qwammy.javaflupskilling_week_5.dtos.product.request.CreateProductDto;
import com.qwammy.javaflupskilling_week_5.dtos.product.response.ReadProductDto;
import com.qwammy.javaflupskilling_week_5.exceptions.product.ProductNotFoundException;

import java.util.List;
import java.util.UUID;

public interface IProductService {

    /**
     * Creates a new product.
     *
     * @param createProductDto The DTO containing product information.
     * @return The DTO representing the created product.
     */
    ReadProductDto createProduct(CreateProductDto createProductDto);

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The DTO representing the retrieved product.
     * @throws RuntimeException if the product with the given ID does not exist.
     */
    ReadProductDto getProductById(UUID id) throws ProductNotFoundException;

    /**
     * Retrieves all products.
     *
     * @return A list of DTOs representing all products.
     */
    List<ReadProductDto> getAllProducts();

    /**
     * Updates an existing product.
     *
     * @param id               The ID of the product to update.
     * @param createProductDto The DTO containing updated product information.
     * @return The DTO representing the updated product.
     * @throws RuntimeException if the product with the given ID does not exist.
     */
    ReadProductDto updateProduct(UUID id, CreateProductDto createProductDto) throws JsonMappingException, ProductNotFoundException;

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @throws RuntimeException if the product with the given ID does not exist.
     */
    void deleteProduct(UUID id) throws ProductNotFoundException;
}
