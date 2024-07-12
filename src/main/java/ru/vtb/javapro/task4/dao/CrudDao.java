package ru.vtb.javapro.task4.dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public interface CrudDao<T, K> {
    void insert(T rec) throws SQLException;
    void update(T rec) throws SQLException;
    void delete(Long id) throws SQLException;
    Optional<T> getUser(K key) throws SQLException;
    List<T> getUsers() throws SQLException;
}
