package ru.vtb.javapro.task4.service;


import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Service;
import ru.vtb.javapro.task4.repository.ProductDAORepository;
import ru.vtb.javapro.task4.entity.Product;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAORepository productDAO;

    public ProductServiceImpl(ProductDAORepository productDao) {
        this.productDAO = productDao;
    }

    @Override
    public void insert(Long clientId, Product rec) throws SQLException {

        productDAO.insert(clientId, rec);
    }

    @Override
    public void update(Long clientId, Product rec) throws SQLException {
        productDAO.update(clientId, rec);
    }

    @Override
    public void delete(Long userId, Long id) throws SQLException {
        productDAO.delete(userId, id);
    }

    @Override
    public void deleteAll(Long userId) throws SQLException {
        productDAO.deleteAll(userId);
    }

    @Override
    public List<Product> getProductsByUserId(Long userId) throws SQLException {
        return productDAO.getProductsByUserId(userId);
    }

    @Override
    public Product getProductsById(Long productId) throws SQLException {
        return productDAO.getProductsById(productId);
    }

}
