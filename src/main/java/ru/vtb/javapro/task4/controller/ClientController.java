package ru.vtb.javapro.task4.controller;


import java.sql.SQLException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.javapro.task4.dao.UserException;
import ru.vtb.javapro.task4.dto.ClientDto;
import ru.vtb.javapro.task4.service.ClientService;


@RestController
@RequestMapping(path = "/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService userService) {
        this.clientService = userService;
    }


    @GetMapping
    public List<ClientDto> getAllClients() throws SQLException {
        return clientService.getClients().stream()
                   .map(c->c.convertToClientDto())
                   .toList();
    }

    @GetMapping("/{id}")
    public ClientDto getClient(@PathVariable Long id) throws SQLException {
        return clientService
                   .getClient(id)
                   .orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "Не найден пользователь с id=" + id))
                   .convertToClientDto();
    }

    @PostMapping
    public Long insertNewUser(
        @RequestBody
        ClientDto client) throws SQLException {
        return clientService.insert(ClientDto.covertToUser(client));
    }

    @PutMapping
    public void updateUser(
        @RequestBody
        ClientDto client) throws SQLException {
        if (client.id() == null)
            throw new UserException(HttpStatus.BAD_REQUEST, "Не указан id");
        clientService.update(ClientDto.covertToUser(client));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(
        @PathVariable
        Long id) throws SQLException {
        if (id != null) {
            clientService.delete(id);
        }
    }
}
