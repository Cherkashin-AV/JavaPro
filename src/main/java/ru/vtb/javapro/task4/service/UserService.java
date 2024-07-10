package ru.vtb.javapro.task4.service;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import ru.vtb.javapro.task4.entity.User;


public interface UserService {

    void insert(User user) throws SQLException;
    void update(User user) throws SQLException;
    void delete(Long id) throws SQLException;
    Optional<User> getUser(Long id) throws SQLException;
    List<User> getUsers() throws SQLException ;
}
