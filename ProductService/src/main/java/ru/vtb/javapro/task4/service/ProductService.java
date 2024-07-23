package ru.vtb.javapro.task4.service;


import java.sql.SQLException;
import java.util.List;
import ru.vtb.javapro.task4.entity.Product;


public interface ProductService {

    void insert(Long clientId, Product rec) throws SQLException;

    void update(Long clientId, Product rec) throws SQLException;

    void delete(Long userId, Long id) throws SQLException;

    void deleteAll(Long userId) throws SQLException;

    List<Product> getProductsByUserId(Long userId) throws SQLException;

    Product getProductsById(Long productId) throws SQLException;
}
