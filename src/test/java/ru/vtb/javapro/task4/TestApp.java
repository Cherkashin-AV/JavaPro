package ru.vtb.javapro.task4;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vtb.javapro.task4.entity.Client;
import ru.vtb.javapro.task4.entity.Product;
import ru.vtb.javapro.task4.entity.Product.ProductType;
import ru.vtb.javapro.task4.service.ClientService;


//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = { AppConfig.class, ClientServiceImpl.class, ClientDao.class, ProductDao.class, ProductController.class, ProductServiceImpl.class})
@SpringBootTest
class TestApp {

    @Autowired
    DataSource dataSource;
//    @Autowired
//    CrudDao crudDao;
    @Autowired
    ClientService userService;

    @Test
    @DisplayName("Проверка внедрения зависимостей")
    void testConfigurations(){
        Assertions.assertNotNull(dataSource);
//        Assertions.assertNotNull(crudDao);
        Assertions.assertNotNull(userService);
    }

    @Test
    @DisplayName("Проверка CRUD-операций класса User")
    void testCrudMethods() throws SQLException {
        Client client = new Client(null, "User Name First");
        client.addProduct(new Product(null, "Продукт1-1", new BigDecimal(100), ProductType.ACCOUNT));
        client.addProduct(new Product(null, "Продукт1-2", new BigDecimal(200), ProductType.CARD));
        //Insert 1 row
        userService.insert(client);
        Assertions.assertNotNull(client.getId());

        //Update
        String newName = "User Name Second";
        client.setUsername(newName);
        userService.update(client);
        Optional<Client> userFromDB = userService.getClient(client.getId());
        Assertions.assertTrue(userFromDB.isPresent());
        Assertions.assertEquals(userFromDB.get().getUsername(), newName);

        //Insert 9 rows
        for (int i = 0; i < 9; i++) {
            userService.insert(new Client(null, String.valueOf(i)));
        }
        List<Client> users = userService.getClients();
        Assertions.assertEquals(10, users.size());


        //Delete 10 rows
        for (Client curUser : users) {
            userService.delete(curUser.getId());
        }
        users = userService.getClients();
        Assertions.assertEquals(0, users.size());
    }
}
