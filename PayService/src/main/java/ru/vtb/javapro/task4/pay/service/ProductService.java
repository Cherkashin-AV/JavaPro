package ru.vtb.javapro.task4.pay.service;


import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vtb.javapro.task4.dto.ProductDTO;



@Service
public class ProductService {
    private final RestTemplate productRestClient;

    public ProductService(RestTemplate productRestClient) {
        this.productRestClient = productRestClient;
    }

    public void checkProducts(Long productId, BigDecimal totalSumm) {
        if(totalSumm == null)
            throw new IllegalArgumentException("Не указана сумма платежа");
        ProductDTO product = productRestClient.getForObject("/products/{productId}", ProductDTO.class, productId);
        if (product == null){
            throw new IllegalArgumentException("Не удалось получить реквизиты продукта " + productId);
        }
        if (product.balance().compareTo(totalSumm)<0){
            throw new IllegalArgumentException("Не достаточно средств на счете");
        }
    }

}
