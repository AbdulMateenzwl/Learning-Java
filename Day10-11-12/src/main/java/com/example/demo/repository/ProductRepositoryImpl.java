package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository // This annotation makes it a Spring Bean
public class ProductRepositoryImpl implements ProductRepository {
    
    private final Map<Long, Product> productDb = new HashMap<>();
    private Long currentId = 1L;

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productDb.values());
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(productDb.get(id));
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            product = new Product(currentId++, product.getName(), product.getPrice(), product.getDescription());
        }
        productDb.put(product.getId(), product);
        return product;
    }

    @Override
    public void deleteById(Long id) {
        productDb.remove(id);
    }
}
