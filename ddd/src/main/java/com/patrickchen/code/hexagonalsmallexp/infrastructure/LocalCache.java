package com.patrickchen.code.hexagonalsmallexp.infrastructure;

import com.patrickchen.code.hexagonalsmallexp.domain.Product;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LocalCache {
    public static ConcurrentHashMap<UUID, Product> CACHE = new ConcurrentHashMap<>();

    public void save(Product product) {
        CACHE.put(product.getId(), product);
    }

    public Product findByProductId(UUID productId) {
        return CACHE.get(productId);
    }
}
