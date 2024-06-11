package com.qwammy.javaflupskilling_week_5.repositories;

import com.qwammy.javaflupskilling_week_5.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByCreatedBy(String createdBy);

    Product findByIdAndCreatedBy(UUID id, String createdBy);
}
