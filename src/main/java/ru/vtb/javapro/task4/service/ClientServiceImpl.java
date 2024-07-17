package ru.vtb.javapro.task4.service;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vtb.javapro.task4.entity.Client;
import ru.vtb.javapro.task4.dao.ClientDao;


@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDao userDao;

    public ClientServiceImpl(@Autowired
    ClientDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Long insert(Client user) throws SQLException {
        return userDao.insert(user);
    }

    @Override
    public void update(Client user) throws SQLException {
        userDao.update(user);
    }

    @Override
    public void delete(Long id) throws SQLException {
        userDao.delete(id);
    }

    @Override
    public Optional<Client> getClient(Long id) throws SQLException {
        return userDao.getEntity(id);
    }

    @Override
    public List<Client> getClients() throws SQLException {
        return userDao.getEntityList();
    }
}
