package ru.vtb.javapro.task4;


import java.sql.SQLException;
import java.util.Arrays;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.vtb.javapro.task4.service.UserService;


public class Application {
    public static void main(String[] args) throws SQLException {
        ApplicationContext appContext = new AnnotationConfigApplicationContext("ru.vtb.javapro.task4");
        //Пример использования контекста в приложении. Полный тест класса UserService в TestApp.
        System.out.println("---- Список бинов: ----");
        Arrays.stream(appContext.getBeanDefinitionNames()).forEach(System.out::println);
        System.out.println("---- Список пользователей из БД: ----");
        UserService userService = appContext.getBean(UserService.class);
        userService.getUsers().forEach(System.out::println);
    }
}
