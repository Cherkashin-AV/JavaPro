package ru.vtb.javapro.task4.controller;


import java.sql.SQLException;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.javapro.task4.dto.ProductDto;
import ru.vtb.javapro.task4.service.ProductService;
import ru.vtb.javapro.task4.service.ProductServiceImpl;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping("/clients/{clientId}/products")
    public List<ProductDto> getProducts(@PathVariable Long clientId) throws SQLException {
        return productService.getProductsByUserId(clientId).stream()
                   .map(p->p.convertToProductDto())
                   .toList();
    }

    @PostMapping("/clients/{clientId}/products")
    public void getProducts(@PathVariable Long clientId, @RequestBody ProductDto product) throws SQLException {
        productService.insert(clientId, product.convertToProduct());
    }

    @PutMapping("/clients/{clientId}/products")
    public void updateProducts(@PathVariable Long clientId, @RequestBody ProductDto product) throws SQLException {
        productService.update(clientId, product.convertToProduct());
    }

    @DeleteMapping("/clients/{clientId}/products/{productId}")
    public void deleteProduct(@PathVariable Long clientId, @PathVariable Long productId) throws SQLException {
        productService.delete(clientId, productId);
    }

    @GetMapping("/products/{productId}")
    public ProductDto getProductById(@PathVariable Long productId) throws SQLException {
        return productService.getProductsById(productId).convertToProductDto();
    }
}
