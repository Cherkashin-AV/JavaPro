package ru.vtb.javapro.task4.pay.service;


import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vtb.javapro.task4.dto.ProductDTO;


@Service
public class ClientService {

    private final RestTemplate clientRestClient;

    public ClientService(RestTemplate clientRestClient) {
        this.clientRestClient = clientRestClient;
    }

    public List<ProductDTO> getProducts(Long clientId) {
        return List.of(
            clientRestClient
                .getForObject("/clients/{clientId}/products", ProductDTO[].class, clientId));
    }

}
