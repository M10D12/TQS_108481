package com.exemplo;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductFinderServiceIT {

    @Test
    void testFindProductDetails_ValidID() {
        ISimpleHttpClient httpClient = new SimpleHttpClient(); // Usa o cliente real
        ProductFinderService service = new ProductFinderService(httpClient);

        Optional<Product> productOpt = service.findProductDetails(3);

        assertTrue(productOpt.isPresent(), "O produto deve estar presente na resposta da API");
        Product product = productOpt.get();
        assertEquals(3, product.getId(), "O ID do produto deve ser 3");
        assertNotNull(product.getTitle(), "O título do produto não deve ser nulo");
        assertNotNull(product.getPrice(), "O preço do produto não deve ser nulo");
        assertNotNull(product.getCategory(), "A categoria não deve ser nula");
    }

    @Test
    void testFindProductDetails_InvalidID() {
        ISimpleHttpClient httpClient = new SimpleHttpClient();
        ProductFinderService service = new ProductFinderService(httpClient);

        Optional<Product> productOpt = service.findProductDetails(300);

        assertTrue(productOpt.isEmpty(), "A resposta deve ser vazia para um ID inexistente");
    }
}
