package ru.vtb.javapro.task4.service;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import ru.vtb.javapro.task4.entity.Client;


public interface ClientService {

    Long insert(Client user) throws SQLException;
    void update(Client user) throws SQLException;
    void delete(Long id) throws SQLException;
    Optional<Client> getClient(Long id) throws SQLException;
    List<Client> getClients() throws SQLException ;
}
