package com.tkpay.vendingmachine.service.impl;

import com.tkpay.vendingmachine.dto.ProductDto;
import com.tkpay.vendingmachine.model.ProductEntity;
import com.tkpay.vendingmachine.repository.ProductRepository;
import com.tkpay.vendingmachine.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    @Override
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductDto> getAllProductDTOs() {
        List<ProductEntity> products = getAllProducts();
        return products
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    @Override
    public ProductEntity getProduct(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product with ID" + id + "not found"));
    }

    @Override
    public ProductDto getProductDTO(Long id) {
        ProductEntity product = getProduct(id);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductEntity saveProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    @Override
    public ProductEntity updateProduct(Long id, ProductEntity product) {
        return productRepository.findById(id)
                .map(myProduct -> {
                    myProduct.setName(product.getName());
                    myProduct.setCurrency(product.getCurrency());
                    myProduct.setAmount(product.getAmount());
                    myProduct.setCount(product.getCount());
                    return productRepository.save(myProduct);
                })
                .orElseGet(() -> productRepository.save(product));
    }

    @Override
    public void checkProductsForTransaction(List<ProductEntity> products) {
        products.forEach(product -> getProduct(product.getId()));
    }
}
