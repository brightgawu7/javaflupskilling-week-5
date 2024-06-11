package com.qwammy.javaflupskilling_week_5.services;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qwammy.javaflupskilling_week_5.dtos.product.request.CreateProductDto;
import com.qwammy.javaflupskilling_week_5.dtos.product.response.ReadProductDto;
import com.qwammy.javaflupskilling_week_5.entities.Product;
import com.qwammy.javaflupskilling_week_5.exceptions.product.ProductNotFoundException;
import com.qwammy.javaflupskilling_week_5.repositories.ProductRepository;
import com.qwammy.javaflupskilling_week_5.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    private final SecurityUtils securityUtils;

    /**
     * Constructs a ProductService with the specified dependencies.
     *
     * @param productRepository the repository used to manage products
     * @param objectMapper      the mapper used to convert between DTOs and entities
     * @param securityUtils     the utility class for security-related operations
     */
    @Autowired
    public ProductService(ProductRepository productRepository, ObjectMapper objectMapper, SecurityUtils securityUtils) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
        this.securityUtils = securityUtils;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadProductDto createProduct(CreateProductDto createProductDto) {
        Product product = objectMapper.convertValue(createProductDto, Product.class);
        product.setCreatedBy(securityUtils.getCurrentUserEmail());
        product = productRepository.save(product);
        return objectMapper.convertValue(product, ReadProductDto.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadProductDto getProductById(UUID id) throws ProductNotFoundException {
        Product product = findProductByIdAndAuthorization(id);
        return objectMapper.convertValue(product, ReadProductDto.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReadProductDto> getAllProducts() {
        List<Product> products;
        String currentUserEmail = securityUtils.getCurrentUserEmail();

        if (securityUtils.isAdmin()) {
            products = productRepository.findAll();
        } else {
            products = productRepository.findByCreatedBy(currentUserEmail);
        }

        return products.stream()
                .map(product -> objectMapper.convertValue(product, ReadProductDto.class))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadProductDto updateProduct(UUID id, CreateProductDto createProductDto) throws JsonMappingException, ProductNotFoundException {
        Product product = findProductByIdAndAuthorization(id);

        objectMapper.updateValue(product, createProductDto);
        product = productRepository.save(product);
        return objectMapper.convertValue(product, ReadProductDto.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProduct(UUID id) throws ProductNotFoundException {
        findProductByIdAndAuthorization(id);
        productRepository.deleteById(id);
    }

    /**
     * Finds a product by its ID and checks user authorization.
     *
     * @param id the UUID of the product
     * @return the product if found and authorized
     * @throws ProductNotFoundException if the product is not found or the user is not authorized
     */
    private Product findProductByIdAndAuthorization(UUID id) throws ProductNotFoundException {
        String currentUserEmail = securityUtils.getCurrentUserEmail();
        Product product;

        if (securityUtils.isAdmin()) {
            product = productRepository.findById(id)
                    .orElseThrow(ProductNotFoundException::new);
        } else {
            product = productRepository.findByIdAndCreatedBy(id, currentUserEmail);
            if (product == null) {
                throw new ProductNotFoundException();
            }
        }

        return product;
    }
}
