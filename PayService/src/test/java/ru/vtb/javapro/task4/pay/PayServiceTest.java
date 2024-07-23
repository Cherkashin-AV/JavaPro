package ru.vtb.javapro.task4.pay;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import ru.vtb.javapro.task4.dto.ProductDTO;
import ru.vtb.javapro.task4.dto.ResponseErrorDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest()
@AutoConfigureMockMvc
class PayServiceTest {

    @MockBean(name = "clientRestClient")
    RestTemplate clientRestClient;

    @MockBean(name = "productRestClient")
    RestTemplate productRestClient;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ApplicationContext context;

    @Test
    @DisplayName("Запрос списка продуктов из ProductService")
    void testClientRestClient() throws Exception {
        Long clientId = 310L;

        ProductDTO[] baseProducts = {
            new ProductDTO(1L, "accNum1", BigDecimal.valueOf(1), "ACCOUNT"),
            new ProductDTO(2L, "accNum2", BigDecimal.valueOf(2), "CARD")
        };

        Mockito
            .when(clientRestClient.getForObject(Mockito.anyString(),Mockito.eq(ProductDTO[].class), Mockito.eq(clientId)))
            .thenReturn(baseProducts);

        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.get("/client/"+clientId+"/products"))
            .andExpect(status().isOk())
            .andReturn();

        ProductDTO[] productsDto = objectMapper.readValue(result.getResponse().getContentAsString(), ProductDTO[].class);

        Assertions.assertEquals(baseProducts.length, productsDto.length);
        Assertions.assertEquals(baseProducts[0], productsDto[0]);
        Assertions.assertEquals(baseProducts[1], productsDto[1]);
    }

    @Test
    @DisplayName("Проверка платежа")
    void testProductRestClient() throws Exception {
        Long clientId = 310L;
        Long productId = 1L;

        ProductDTO baseProduct = new ProductDTO(productId,"AccNumber1", BigDecimal.valueOf(100), "CARD");

        Mockito
            .when(productRestClient.getForObject(Mockito.anyString(), Mockito.eq(ProductDTO.class), Mockito.anyLong()))
            .thenReturn(baseProduct);

        mockMvc.perform(MockMvcRequestBuilders.put("/client/"+clientId+"/products/"+productId+"?sum=100"))
            .andExpect(status().isOk())
            .andReturn();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/client/"+clientId+"/products/"+productId+"?sum=101"))
            .andExpect(status().isBadRequest())
            .andReturn();

        ResponseErrorDto productsDto = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), ResponseErrorDto.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,productsDto.status());
        Assertions.assertEquals("Не достаточно средств на счете", productsDto.message());

    }
}
