package by.project.usermanagementsystem.dao.service;

import by.project.usermanagementsystem.dao.ClientDao;
import by.project.usermanagementsystem.domain.Client;
import by.project.usermanagementsystem.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ClientService implements ClientDao {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Client save(Client client) {
        return userRepository.save(client);
    }

    @Override
    public void delete(List<Client> clientList) {
        userRepository.delete(clientList);
    }

    @Override
    public List<Client> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void changeRole(String text, long id) {
        userRepository.changeRole(text, id);
    }

    @Override
    public void updateLastEntrance(Date date, String name) {
        userRepository.updateLastEntrance(date, name);
    }

    @Override
    public Client findByName(String name) {
        return userRepository.findByName(name);
    }
}
