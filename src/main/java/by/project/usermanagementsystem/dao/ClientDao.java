package by.project.usermanagementsystem.dao;

import by.project.usermanagementsystem.domain.Client;

import java.util.Date;
import java.util.List;

public interface ClientDao {

    Client save (Client client);

    void delete (List<Client> clientList);

    List<Client> getAll();

    void changeRole(String text, long id);

    void updateLastEntrance(Date date, String name);

    Client findByName(String name);
}
