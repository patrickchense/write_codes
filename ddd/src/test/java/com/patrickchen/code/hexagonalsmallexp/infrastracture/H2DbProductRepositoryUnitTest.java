package com.patrickchen.code.hexagonalsmallexp.infrastracture;

import com.patrickchen.code.hexagonalsmallexp.domain.Product;
import com.patrickchen.code.hexagonalsmallexp.infrastructure.ProductRepostiory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class H2DbProductRepositoryUnitTest {

    private ProductRepostiory productRepostiory;

    @BeforeEach
    void setUp() {
        productRepostiory = mock(ProductRepostiory.class);
    }

    @Test
    void shouldFindById_thenReturnProduct() {
        UUID id = UUID.randomUUID();
        Product product = createProduct(id);
        when(productRepostiory.findById(id)).thenReturn(product);

        Product result = productRepostiory.findById(id);

        assertEquals(product, result);
    }

    @Test
    void shouldSaveProduct_thenVerify() {
        UUID id = UUID.randomUUID();
        Product product = createProduct(id);

        productRepostiory.save(product);

        verify(productRepostiory).save(product);
    }

    private Product createProduct(UUID id) {
        return new Product(id, 10);
    }
}
