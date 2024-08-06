package ru.vtb.javapro.task4.service;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.vtb.javapro.task4.repository.ClientJPARepository;
import ru.vtb.javapro.task4.entity.Client;


@Service
@Primary
public class ClientServiceJPA implements  ClientService{

    private final ClientJPARepository clientJPARepository;

    public ClientServiceJPA(ClientJPARepository clientJPARepository) {
        this.clientJPARepository = clientJPARepository;
    }

    @Override
    public Long insert(Client user) throws SQLException {
        clientJPARepository.save(user);
        return user.getId();
    }

    @Override
    public void update(Client user) throws SQLException {
        clientJPARepository.save(user);
    }

    @Override
    public void delete(Long id) throws SQLException {
        clientJPARepository.deleteById(id);
    }

    @Override
    public Optional<Client> getClient(Long id) throws SQLException {
        return clientJPARepository.findById(id);
    }

    @Override
    public List<Client> getClients() throws SQLException {
        return clientJPARepository.findAll();
    }
}
