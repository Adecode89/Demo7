package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product criar(Product product) {
        if (product.getPrice() != null && product.getPrice().doubleValue() < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo");
        }
        return productRepository.save(product);
    }


    public List<Product> listarTodos() {
        return productRepository.findAll();
    }


    public Optional<Product> buscarPorId(Long id) {
        return productRepository.findById(id);
    }


    public Product atualizar(Long id, Product productAtualizado) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(productAtualizado.getName());
                    product.setDescription(productAtualizado.getDescription());
                    product.setPrice(productAtualizado.getPrice());
                    product.setStockQuantity(productAtualizado.getStockQuantity());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));
    }


    public void deletar(Long id) {
        productRepository.deleteById(id);
    }
}