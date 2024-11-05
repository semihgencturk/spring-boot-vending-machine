package com.tkpay.vendingmachine.service;

import com.tkpay.vendingmachine.dto.ProductDto;
import com.tkpay.vendingmachine.model.ProductEntity;

import java.util.List;

public interface ProductService {
    List<ProductEntity> getAllProducts();
    List<ProductDto> getAllProductDTOs();
    ProductEntity getProduct(Long id);
    ProductDto getProductDTO(Long id);
    ProductEntity saveProduct(ProductEntity product);
    ProductEntity updateProduct(Long id, ProductEntity product);
    void checkProductsForTransaction(List<ProductEntity> products);
}
