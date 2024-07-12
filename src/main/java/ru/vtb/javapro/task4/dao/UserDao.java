package ru.vtb.javapro.task4.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vtb.javapro.task4.entity.User;


@Component
public class UserDao implements CrudDao<User, Long> {

    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    public static final String INSERT_INTO_USERS        = "Insert into users (username) values(?)";
    public static final String UPDATE_USERS             = "Update users set username = ? where id = ?";
    public static final String DELETE_FROM_USERS        = "Delete from users where id=?";
    public static final String SELECT_FROM_USERS_BY_ID  = "Select id, username from users where id=?";
    public static final String SELECT_FROM_USERS        = "Select id, username from users";

    private final DataSource dataSource;

    public UserDao(@Autowired DataSource dataSource) {
        this.dataSource = dataSource;
    }

    Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void insert(User rec) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(
            INSERT_INTO_USERS,
            Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, rec.getUsername());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        rec.setId(generatedKeys.getLong("id"));
                    }
                }
            }
        }
    }

    @Override
    public void update(User rec) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(UPDATE_USERS)) {
            ps.setString(1, rec.getUsername());
            ps.setLong(2, rec.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(
            DELETE_FROM_USERS)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Optional<User> getUser(Long id) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(
            SELECT_FROM_USERS_BY_ID)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new User(resultSet.getLong("id"), resultSet.getString("username")));
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public List<User> getUsers() throws SQLException {
        List<User> result = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(
            SELECT_FROM_USERS)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result.add(new User(resultSet.getLong("id"), resultSet.getString("username")));
            }
        }
        return result;
    }
}
