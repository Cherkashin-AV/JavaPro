package ru.vtb.javapro.task4;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.vtb.javapro.task4.dto.ClientDto;
import ru.vtb.javapro.task4.dto.ProductDto;
import ru.vtb.javapro.task4.entity.Client;
import ru.vtb.javapro.task4.entity.Product.ProductType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TestRestClient {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Запрос списка всех клиентов с продуктами")
    void requestUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
            .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Проверка запросов из задания (продукты по UserId и по id)")
    void complexTest() throws Exception {
        String accN1="Acc1", accN2="Acc2";

        ClientDto client = new ClientDto(
            null,
            "Тестовый клиент",
            Arrays.asList(
                new ProductDto(null, accN1, BigDecimal.valueOf(1),  ProductType.CARD.name()),
                new ProductDto(null, accN2,  BigDecimal.valueOf(2), ProductType.ACCOUNT.name())
                )
        );

        //Создаем нового клиента
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/clients")
            .content(objectMapper.writeValueAsString(client)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
        Long clientId = Long.valueOf(result.getResponse().getContentAsString());

        //Запрашиваем данные по созданному клиенту
        result = mockMvc.perform(MockMvcRequestBuilders.get("/clients/"+clientId)
            .content(objectMapper.writeValueAsString(client)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        ClientDto clientDto = objectMapper.readValue(result.getResponse().getContentAsString(), ClientDto.class);
        Assertions.assertEquals(2, clientDto.products().size());
        Assertions.assertEquals(accN1, clientDto.products().get(0).accontNumber());
        Assertions.assertEquals(accN2, clientDto.products().get(1).accontNumber());

        //Запрашиваем данные по productId
        Long productId = clientDto.products().get(0).id();
        result = mockMvc.perform(MockMvcRequestBuilders.get("/products/"+productId)
                                     .content(objectMapper.writeValueAsString(client)).contentType(MediaType.APPLICATION_JSON))
                     .andExpect(status().isOk())
                     .andReturn();
        ProductDto productDto = objectMapper.readValue(result.getResponse().getContentAsString(), ProductDto.class);
        Assertions.assertEquals(accN1, productDto.accontNumber());
        Assertions.assertEquals(ProductType.CARD.name(), productDto.productType());
        Assertions.assertEquals(BigDecimal.valueOf(1), productDto.balance());
        Assertions.assertEquals(productId, productDto.id());

        //Удаляем созданного клиента
        mockMvc.perform(MockMvcRequestBuilders.delete("/clients/"+clientId)
                            .content(objectMapper.writeValueAsString(client)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    }

}
