package br.com.tgidSimpleBank.SimpleBank.services;

import br.com.tgidSimpleBank.SimpleBank.models.users.User;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Client;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Company;
import br.com.tgidSimpleBank.SimpleBank.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    @Test
    void testListAllUsers() {
        List<User> users = new ArrayList<>(List.of(new Client(), new Company()));

        when(repository.findAll()).thenReturn(users);

        List<User> responseUsers = service.listAllUsers();

        assertEquals(users, responseUsers);
        verify(repository, times(1)).findAll();
    }

    @Test
    void testEmptyList() {
        List<User> users = new ArrayList<>();

        when(repository.findAll()).thenReturn(users);

        assertThrows(EntityNotFoundException.class, () -> service.listAllUsers());
        verify(repository, times(1)).findAll();
    }
}
