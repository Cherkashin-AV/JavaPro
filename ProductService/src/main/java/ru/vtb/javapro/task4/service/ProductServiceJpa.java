package ru.vtb.javapro.task4.service;


import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.vtb.javapro.task4.repository.ClientJPARepository;
import ru.vtb.javapro.task4.repository.ProductJPARepository;
import ru.vtb.javapro.task4.entity.Product;


@Service
@Primary
public class ProductServiceJpa implements ProductService{
    ProductJPARepository productRepository;
    ClientJPARepository clientRepository;

    @Override
    public void insert(Long clientId, Product rec) throws SQLException {
        Product product = productRepository.getProduct(clientId, rec.getId())
                              .orElseThrow(()->new SQLDataException("Не найден клиент с ID="+clientId));

        product.setProductType(rec.getProductType());
        product.setBalance(rec.getBalance());
        product.setAccountNumber(rec.getAccountNumber());
        productRepository.save(product);
    }

    @Override
    public void update(Long clientId, Product rec) throws SQLException {
        productRepository.save(rec);
    }

    @Override
    public void delete(Long userId, Long id) throws SQLException {
        productRepository.delete(userId, id);
    }

    @Override
    public void deleteAll(Long userId) throws SQLException {
        productRepository.deleteAll(userId);
    }

    @Override
    public List<Product> getProductsByUserId(Long clientId) throws SQLException {
        return clientRepository
                            .findById(clientId)
                            .orElseThrow(()->new SQLDataException("Не найден клиент с ID="+clientId)).getProductList();
    }

    @Override
    public Product getProductsById(Long productId) throws SQLException {
        return productRepository.findById(productId)
                   .orElseThrow(()->new SQLDataException("Не найден продукт с ID="+productId));
    }
}
