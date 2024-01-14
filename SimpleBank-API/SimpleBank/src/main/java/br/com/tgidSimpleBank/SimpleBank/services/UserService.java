package br.com.tgidSimpleBank.SimpleBank.services;

import br.com.tgidSimpleBank.SimpleBank.models.users.User;
import br.com.tgidSimpleBank.SimpleBank.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> listAllUsers() {
        List<User> users = repository.findAll();

        if (users.isEmpty()) {
            throw new EntityNotFoundException("Usuários não encontrados");
        }

        return users;
    }

    public User validateLoginByEmail(User user) {
        User userOnDB = repository.findByEmail(user.getEmail());

        if (userOnDB == null || !userOnDB.getPassword().equals(user.getPassword())) {
            throw new EntityNotFoundException("Dados do usuário incorretos");
        }

        return userOnDB;
    }
}
