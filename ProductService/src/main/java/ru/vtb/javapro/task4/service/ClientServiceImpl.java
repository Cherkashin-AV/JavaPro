package ru.vtb.javapro.task4.service;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vtb.javapro.task4.entity.Client;
import ru.vtb.javapro.task4.Repository.ClientRepository;


@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientDao;

    public ClientServiceImpl(@Autowired
    ClientRepository userDao) {
        this.clientDao = userDao;
    }

    @Override
    public Long insert(Client user) throws SQLException {
        return clientDao.insert(user);
    }

    @Override
    public void update(Client user) throws SQLException {
        clientDao.update(user);
    }

    @Override
    public void delete(Long id) throws SQLException {
        clientDao.delete(id);
    }

    @Override
    public Optional<Client> getClient(Long id) throws SQLException {
        return clientDao.getEntity(id);
    }

    @Override
    public List<Client> getClients() throws SQLException {
        return clientDao.getEntityList();
    }
}
