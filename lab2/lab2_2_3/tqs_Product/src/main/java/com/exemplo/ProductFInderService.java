package com.exemplo;

import java.util.Optional;

public class ProductFInderService {
    public ISImpleHttpClient httpClient;
    private String API_PRODUCTS;

    public ProductFInderService(ISImpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }


    public Optional<Product> findProductDetails(int id) {
        Product product = null;
        return Optional.ofNullable(product);
    }
}
