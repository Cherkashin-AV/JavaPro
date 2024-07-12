package ru.vtb.javapro.task4;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.vtb.javapro.task4.dao.CrudDao;
import ru.vtb.javapro.task4.entity.User;
import ru.vtb.javapro.task4.dao.UserDao;
import ru.vtb.javapro.task4.service.UserService;
import ru.vtb.javapro.task4.service.UserServiceImpl;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class, UserServiceImpl.class, UserDao.class})
class TestApp {

    @Autowired
    DataSource dataSource;
    @Autowired
    CrudDao crudDao;
    @Autowired
    UserService userService;

    @Test
    @DisplayName("Проверка внедрения зависимостей")
    void testConfigurations(){
        Assertions.assertNotNull(dataSource);
        Assertions.assertNotNull(crudDao);
        Assertions.assertNotNull(userService);
    }

    @Test
    @DisplayName("Проверка CRUD-операций класса User")
    void testCrudMethods() throws SQLException {
        User user = new User(null, "User Name First");
        //Insert 1 row
        userService.insert(user);
        Assertions.assertNotNull(user.getId());

        //Update
        String newName = "User Name Second";
        user.setUsername(newName);
        userService.update(user);
        Optional<User> userFromDB = userService.getUser(user.getId());
        Assertions.assertTrue(userFromDB.isPresent());
        Assertions.assertEquals(userFromDB.get().getUsername(), newName);

        //Insert 9 rows
        for (int i = 0; i < 9; i++) {
            userService.insert(new User(null, String.valueOf(i)));
        }
        List<User> users = userService.getUsers();
        Assertions.assertEquals(10, users.size());

        //Delete 10 rows
        for (User curUser : users) {
            userService.delete(curUser.getId());
        }
        users = userService.getUsers();
        Assertions.assertEquals(0, users.size());
    }
}
