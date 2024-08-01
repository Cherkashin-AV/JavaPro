package ru.vtb.javapro.task4.pay.controller;


import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.vtb.javapro.task4.dto.ProductDTO;
import ru.vtb.javapro.task4.pay.service.ClientService;
import ru.vtb.javapro.task4.pay.service.ProductService;


@RestController
public class PayController {

    private final ProductService productService;
    private final ClientService clientService;

    public PayController(ProductService productService, ClientService clientService) {
        this.productService = productService;
        this.clientService = clientService;
    }

    @GetMapping("/client/{clientId}/products")
    public List<ProductDTO> getProducts(@PathVariable Long clientId){
        return clientService.getProducts(clientId);
    }

    @PutMapping("/client/{clientId}/products/{productId}")
    public void checkProducts(
        @PathVariable Long clientId,
        @PathVariable Long productId,
        @RequestParam("sum") BigDecimal totalSum
        ){
        productService.checkProducts(productId, totalSum);
    }
}
