package ru.vtb.javapro.task4.service;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vtb.javapro.task4.dao.CrudDao;
import ru.vtb.javapro.task4.entity.User;
import ru.vtb.javapro.task4.dao.UserDao;


@Service
public class UserServiceImpl implements UserService {

    private final CrudDao<User, Long> userDao;

    public UserServiceImpl(@Autowired UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void insert(User user) throws SQLException {
        userDao.insert(user);
    }

    @Override
    public void update(User user) throws SQLException {
        userDao.update(user);
    }

    @Override
    public void delete(Long id) throws SQLException {
        userDao.delete(id);
    }

    @Override
    public Optional<User> getUser(Long id) throws SQLException {
        return userDao.getUser(id);
    }

    @Override
    public List<User> getUsers() throws SQLException {
        return userDao.getUsers();
    }
}
