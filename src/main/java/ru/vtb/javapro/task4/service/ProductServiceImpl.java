package ru.vtb.javapro.task4.service;


import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Service;
import ru.vtb.javapro.task4.dao.ProductDao;
import ru.vtb.javapro.task4.entity.Product;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void insert(Long clientId, Product rec) throws SQLException {

        productDao.insert(clientId, rec);
    }

    @Override
    public void update(Long clientId, Product rec) throws SQLException {
        productDao.update(clientId, rec);
    }

    @Override
    public void delete(Long userId, Long id) throws SQLException {
        productDao.delete(userId, id);
    }

    @Override
    public void deleteAll(Long userId) throws SQLException {
        productDao.deleteAll(userId);
    }

    @Override
    public List<Product> getProductsByUserId(Long userId) throws SQLException {
        return productDao.getProductsByUserId(userId);
    }

    @Override
    public Product getProductsById(Long productId) throws SQLException {
        return productDao.getProductsById(productId);
    }

}
