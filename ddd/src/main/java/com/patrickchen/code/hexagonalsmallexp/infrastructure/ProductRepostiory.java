package com.patrickchen.code.hexagonalsmallexp.infrastructure;

import com.patrickchen.code.hexagonalsmallexp.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepostiory {

    void save(Product product);

    Product findById(UUID productId);
}
