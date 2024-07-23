package ru.vtb.javapro.task4.pay;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import ru.vtb.javapro.task4.dto.ProductDTO;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RestServiceServerTest {

    private MockRestServiceServer mockServer;

    @Autowired
    @Qualifier(value = "clientRestClient")
    RestTemplate clientRestClient;


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(clientRestClient);
    }

    @Test
    void test() throws Exception {
        Long clientId = 310L;

        ProductDTO[] baseProducts = {
            new ProductDTO(1L, "accNum1", BigDecimal.valueOf(1), "ACCOUNT"),
            new ProductDTO(2L, "accNum2", BigDecimal.valueOf(2), "CARD")
        };

        mockServer
            .expect(
                ExpectedCount.once(),
                requestTo(new URI("http://localhost:8080/clients/"+clientId+"/products")))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(objectMapper.writeValueAsString(baseProducts)));

        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.get("/client/"+clientId+"/products"))
                                .andExpect(status().isOk())
                                .andReturn();

        ProductDTO[] productsDto = objectMapper.readValue(result.getResponse().getContentAsString(), ProductDTO[].class);

        Assertions.assertEquals(baseProducts.length, productsDto.length);
        Assertions.assertEquals(baseProducts[0], productsDto[0]);
        Assertions.assertEquals(baseProducts[1], productsDto[1]);

    }
}


