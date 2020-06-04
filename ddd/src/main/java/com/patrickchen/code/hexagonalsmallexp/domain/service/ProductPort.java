package com.patrickchen.code.hexagonalsmallexp.domain.service;

import com.patrickchen.code.hexagonalsmallexp.domain.Product;

import java.util.UUID;

public interface ProductPort {

    void save(Product product);

    Product findByProductId(UUID productId);
}
