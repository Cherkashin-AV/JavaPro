package ru.vtb.javapro.task4.dto;


import java.util.List;
import ru.vtb.javapro.task4.entity.Client;


public record ClientDto(Long id, String clientName, List<ProductDto> products)
{
    public static ClientDto covertFromUser(Client user){
        return new ClientDto(user.getId(), user.getUsername(), user.getProducts().stream().map(p->p.convertToProductDto()).toList());
    }

    public static Client covertToUser(ClientDto clientDto){
        Client result = new Client(clientDto.id(), clientDto.clientName());
        for(ProductDto productDto : clientDto.products()) {
            result.addProduct(productDto.convertToProduct());
        }
        return result;
    }
}
