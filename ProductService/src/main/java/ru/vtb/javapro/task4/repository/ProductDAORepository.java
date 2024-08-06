package ru.vtb.javapro.task4.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import ru.vtb.javapro.task4.entity.Product;
import ru.vtb.javapro.task4.entity.Product.ProductType;


@Repository
public class ProductDAORepository {

    private static final Logger log = LoggerFactory.getLogger(ProductDAORepository.class);


    private final Connection connection;

    public ProductDAORepository(Connection connection) {
        this.connection = connection;
    }

    private Long getProductsId(Long clientId) throws SQLException {
        String sql = "Select products from users where id = ?";
        Long productsId;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1, clientId);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            productsId = resultSet.getLong("products");
        }
        return  productsId;
    }

    public Long insert(Long productsId, Product rec) throws SQLException {
        Long result = 0L;
        String sql = "Insert into products (productsid, accountNumber, balance, productType) values(?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, productsId);
            ps.setString(2, rec.getAccountNumber());
            ps.setBigDecimal(3, rec.getBalance());
            ps.setInt(4, rec.getProductType()
                             .getCode());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        result = generatedKeys.getLong("id");
                        rec.setId(result);
                    }
                }
            }
        }
        return result;
    }

    public void deleteAll(Long userId) throws SQLException {
        String sql = "Delete from products where productsid = (Select products from  users where id = ?)";
        try (PreparedStatement ps = connection.prepareStatement(
            sql)) {
            ps.setLong(1, userId);
            ps.executeUpdate();
        }
    }

    public void delete(Long userId, Long id) throws SQLException {
        String sql = "Delete from products where productsid = (Select products from  users where id = ?) and id = ?";
        try (PreparedStatement ps = connection.prepareStatement(
            sql)) {
            ps.setLong(1, userId);
            ps.setLong(2, id);
            ps.executeUpdate();
        }
    }


    public void update(Long clientId, Product product) throws SQLException {
        String sql = "Update products set accountNumber=?, balance=?, producttype=? where id=? and productsid=?";
        try (PreparedStatement ps = connection.prepareStatement(
            sql)) {
            ps.setString(1, product.getAccountNumber());
            ps.setBigDecimal(2, product.getBalance());
            ps.setInt(3, product.getProductType().getCode());
            ps.setLong(4, product.getId());
            ps.setLong(5, getProductsId(clientId));
            ps.executeUpdate();
        }
    }

    public List<Product> getProductsByUserId(Long userId) throws SQLException {
        List<Product> result = new ArrayList<>();
        String sql = "Select id, productsid, accountNumber , balance , producttype from products where productsid = (Select products from  users where id = ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result.add(new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("accountNumber"),
                    resultSet.getBigDecimal("balance"),
                    ProductType.getInstanceByCode(resultSet.getInt("producttype"))
                    )
                );
            }
            return result;
        }

    }

    public Product getProductsById(Long productsid) throws SQLException {
        String sql = "Select id, productsid, accountNumber , balance , producttype from products where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, productsid);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("accountNumber"),
                        resultSet.getBigDecimal("balance"),
                        ProductType.getInstanceByCode(resultSet.getInt("producttype"))
                );
            }
            throw new ProductException(HttpStatus.NOT_FOUND, "Не найден продукт с id="+productsid);
        }

    }
}

