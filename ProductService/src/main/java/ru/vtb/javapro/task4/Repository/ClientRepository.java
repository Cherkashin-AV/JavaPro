package ru.vtb.javapro.task4.Repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.vtb.javapro.task4.entity.Client;
import ru.vtb.javapro.task4.entity.Product;


@Component
public class ClientRepository {

    private static final Logger log = LoggerFactory.getLogger(ClientRepository.class);

    private final Connection connection;
    private final ProductRepository productDao;

    public ClientRepository(Connection connection, ProductRepository productDao) {
        this.connection = connection;
        this.productDao = productDao;
    }

    public Long insert(Client rec) throws SQLException {
        Long result = 0l;
        String sql = "Insert into users (username) values(?)";
        connection.setAutoCommit(false);
        try (PreparedStatement ps = connection.prepareStatement(
            sql,
            Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, rec.getUsername());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        result = generatedKeys.getLong("id");
                        rec.setId(result);
                        Long producstid = generatedKeys.getLong("products");
                        for (Product product: rec.getProducts()) {
                            productDao.insert(producstid, product);
                        }
                    }
                }
            }
        } catch (SQLException ex){
            connection.rollback();
            throw ex;
        }
        finally {
            if (!connection.isClosed()){
                connection.commit();
            }
            connection.setAutoCommit(true);
        }
        return result;
    }

    public void delete(Long id) throws SQLException {
        String sql = "Delete from users where id=?";
        connection.setAutoCommit(false);
        try (PreparedStatement ps = connection.prepareStatement(
            sql)) {
            productDao.deleteAll(id);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex){
            connection.rollback();
            throw ex;
        }
        finally {
            if (!connection.isClosed()){
                connection.commit();
            }
            connection.setAutoCommit(true);
        }
    }


    public void update(Client rec) throws SQLException {
        String sql = "Update users set username = ? where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, rec.getUsername());
            ps.setLong(2, rec.getId());
            ps.executeUpdate();
        }
    }

    public Optional<Client> getEntity(Long id) throws SQLException {
        String sql = "Select id, username from users where id=?";
        try (PreparedStatement ps = connection.prepareStatement(
            sql)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Client result = new Client(resultSet.getLong("id"), resultSet.getString("username"));
                for(Product p :productDao.getProductsByUserId(result.getId())){
                    result.addProduct(p);
                }
                return Optional.of(result);
            } else {
                return Optional.empty();
            }
        }
    }

    public List<Client> getEntityList() throws SQLException {
        String sql = "Select id, username from users";
        List<Client> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
            sql)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Client client = new Client(resultSet.getLong("id"), resultSet.getString("username"));
                for(Product p :productDao.getProductsByUserId(client.getId())){
                    client.addProduct(p);
                }
                result.add(client);
            }
        }
        return result;
    }
}
