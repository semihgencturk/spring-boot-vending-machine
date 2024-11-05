package com.tkpay.vendingmachine.controller;

import com.tkpay.vendingmachine.dto.ProductDto;
import com.tkpay.vendingmachine.model.ProductEntity;
import com.tkpay.vendingmachine.service.ProductService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController implements BaseController {

    private final ProductService productService;

    @GetMapping("/products")
    @JsonView(ProductDto.Basic.class)
    public List<ProductDto> getProductDTOs() {
        return productService.getAllProductDTOs();
    }

    @GetMapping("/products/{id}")
    @JsonView(ProductDto.Detail.class)
    public ProductDto getProductDTO(@PathVariable("id") Long id) {
        return productService.getProductDTO(id);
    }

    @PostMapping("/products")
    public ProductEntity createProduct(@RequestBody ProductEntity product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/products/{id}")
    public ProductEntity updateProduct(@PathVariable("id") Long id, @RequestBody @Valid ProductEntity product) {
        return productService.updateProduct(id, product);
    }

}
